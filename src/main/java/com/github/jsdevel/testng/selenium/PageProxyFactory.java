package com.github.jsdevel.testng.selenium;

import com.github.jsdevel.testng.selenium.exceptions.PageInstantiationException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class PageProxyFactory {
  private static final Map<Class<? extends Browser>, PageProxy.Builder>
      pageProxyBuilders = new ConcurrentHashMap<>();

  static void registerPage(Class<? extends Browser> pageInterface,
      Class<? extends PageFactory> pageFactoryClass) {
    if (!pageProxyBuilders.containsKey(pageInterface)) {
      assertAllInterfacesHaveImpls(pageInterface);
      Map<Method, Class> implClasses = getImplClasses(pageInterface);
      PageProxy.Builder pageProxyBuilder = new PageProxy.Builder();
      pageProxyBuilder.currentPageInterface = pageInterface;
      pageProxyBuilder.implClasses = implClasses;
      pageProxyBuilder.methodImpls = getMethodImpls(implClasses);

      //Run this before we gather returned pages to ensure the return type isn't
      //coming from method type parameters.
      assertNoMethodAcceptsTypeParameters(pageProxyBuilder);

      pageProxyBuilder.returnedPages = getReturnedPages(pageInterface,
          getTypeArguments(pageInterface, null));
      assertAllPagesAreHandledByThePageFactory(pageProxyBuilder,
          pageFactoryClass);
      assertAllInterfaceMethodsHaveTheDeclaringClassNameInThem(pageProxyBuilder);

      pageProxyBuilders.put(pageInterface, pageProxyBuilder);
    }
  }

  static PageProxy getPageProxy(Class<? extends Browser> pageInterface) {
    return pageProxyBuilders.get(pageInterface).build();
  }

  private static Map<Method, Class<? extends Browser>> getReturnedPages(
      Class<? extends Browser> pageInterface,
      Map<Class, Map<String, Class<? extends Browser>>> typeArguments) {
    Map<Method, Class<? extends Browser>> returnedPages = new LinkedHashMap<>();

    Arrays.asList(pageInterface.getMethods()).forEach(method -> {
      if (Browser.class.isAssignableFrom(method.getReturnType())) {
        returnedPages.put(method, (Class<? extends Browser>) method
            .getReturnType());
      } else {
        Map<String, Class<? extends Browser>> typeParameters =
            typeArguments.get(method.getDeclaringClass());

        if (typeParameters != null) {
          Class<? extends Browser> returnedPage = typeParameters.get(
              method.getGenericReturnType().getTypeName());

          if (returnedPage != null) {
            returnedPages.put(method, returnedPage);
            return;
          }
        }

        throw new IllegalStateException("Unknown return type for " +
            method.getDeclaringClass().getName() + "#" + method.getName() +
            ".  This is most likely a bug in TestNG-Selenium.  " +
            "Please report this.");
      }
    });

    return returnedPages;
  }

  private static void assertAllInterfaceMethodsHaveTheDeclaringClassNameInThem (
      PageProxy.Builder pageProxyBuilder) {

    Map<Class, StringBuilder> methodsNotContainingInterfaceNamesByClass =
        new HashMap<>();

    pageProxyBuilder.methodImpls.keySet().forEach(method -> {
      Class declaringClass = method.getDeclaringClass();
      String simpleName = declaringClass.getSimpleName();
      String methodName = method.getName();
      if (methodName.indexOf(simpleName) > 0 == false) {
        StringBuilder badMethods;
        if (!methodsNotContainingInterfaceNamesByClass.containsKey(declaringClass)) {
          badMethods = new StringBuilder();
          methodsNotContainingInterfaceNamesByClass.put(declaringClass, badMethods);
          badMethods.append(methodName);
        } else {
          methodsNotContainingInterfaceNamesByClass.get(declaringClass)
              .append(", ")
              .append(methodName);
        }
      }
    });

    if (methodsNotContainingInterfaceNamesByClass.size() > 0) {
      StringBuilder msg = new StringBuilder();
      methodsNotContainingInterfaceNamesByClass.entrySet().forEach(entry -> {
        if (msg.length() > 0) {
          msg.append("\n  ");
        }
        msg
          .append(entry.getKey().getName())
          .append(": ")
          .append(entry.getValue());
      });

      throw new PageInstantiationException(
          "The following methods failed to include the name of their declaring class in their name:\n  " +
              msg);
    }
  }

  private static void assertAllInterfacesHaveImpls (Class clazz) {
    getImplClass(clazz);
    Arrays.asList(clazz.getInterfaces()).forEach(inerface -> {
      getImplClass(inerface);
    });
  }

  private static void assertAllPagesAreHandledByThePageFactory(
      PageProxy.Builder pageProxyBuilder,
      Class<? extends PageFactory> pageFactoryClass) {
    StringBuilder pagesNotHandledByPageFactory = new StringBuilder();

    pageProxyBuilder.returnedPages.entrySet().forEach(entry -> {
      Class pageClass = entry.getValue();

      try {
        pageFactoryClass.getMethod("get" + pageClass.getSimpleName());
      } catch(NoSuchMethodException e) {
        if (pagesNotHandledByPageFactory.length() > 0) {
          pagesNotHandledByPageFactory.append(", ");
        }
        pagesNotHandledByPageFactory.append(pageClass.getName());
      }
    });

    if (pagesNotHandledByPageFactory.length() > 0) {
        throw new PageInstantiationException(
            "The following pages were not handled by " +
            pageFactoryClass.getName() + 
            ": " + pagesNotHandledByPageFactory);
    }
  }

  private static void assertNoMethodAcceptsTypeParameters(PageProxy.Builder pageProxyBuilder) {
    StringBuilder methodsContainingTypeParameters = new StringBuilder();

    pageProxyBuilder.methodImpls.entrySet().forEach(entry -> {
      Method inerfaceMethod = entry.getKey();
      Method implMethod = entry.getValue();
      List<Method> containingTypeParameters = new ArrayList<>();

      if (inerfaceMethod.getTypeParameters().length > 0) {
        containingTypeParameters.add(inerfaceMethod);
      }

      if (implMethod.getTypeParameters().length > 0) {
        containingTypeParameters.add(implMethod);
      }

      containingTypeParameters.forEach(method -> {
        if (methodsContainingTypeParameters.length() > 0) {
          methodsContainingTypeParameters.append(", ");
        }
        methodsContainingTypeParameters
            .append(method.getDeclaringClass().getName())
            .append("#")
            .append(method.getName());
      });
    });

    if (methodsContainingTypeParameters.length() > 0) {
        throw new PageInstantiationException(
            "The following methods accept Type parameters: " +
            methodsContainingTypeParameters);
    }
  }

  private static Map<Method, Method> getMethodImpls(
      Map<Method, Class> implClasses) {

    Map<Method, Method> methodImpls = new HashMap<>();

    implClasses.entrySet().forEach(entry -> {
      Method method = entry.getKey();
      Class impl = entry.getValue();

      try {
        Method methodImpl;

        if (method.getParameterCount() > 0) {
          methodImpl = impl.getMethod(method.getName(),
            method.getParameterTypes());
        } else {
          methodImpl = impl.getMethod(method.getName());
        }

        if (!methodImpl.getReturnType().equals(Void.TYPE)) {
          throw new NoSuchMethodException();
        }

        methodImpls.put(method, methodImpl);
      } catch (NoSuchMethodException ex) {
        throw new PageInstantiationException(
            "Expected to see a public method with signature [" +
            expectedImplMethodSignature(method) + "] in " + impl.getName());
      }
    });

    return methodImpls;
  }

  private static Map<Method, Class> getImplClasses(Class pageInterface) {
    Map<Method, Class> implClasses = new HashMap<>();

    Arrays.asList(pageInterface.getMethods()).forEach(method -> {
      implClasses.put(method, getImplClass(method.getDeclaringClass()));
    });

    return implClasses;
  }

  private static Class getImplClass(Class clazz) {
    String name = clazz.getName();
    String implName = name + "Impl";
    try {
      return clazz.getClassLoader().loadClass(implName);
    } catch (ClassNotFoundException ex) {
      throw new PageInstantiationException("Could not load " + implName +
          ".  Does it exist?");
    }
  }

  private static Map<Class, Map<String, Class<? extends Browser>>>
      getTypeArguments(Class inerface,
          Map<Class, Map<String, Class<? extends Browser>>> proposedParameters) {
    final Map<Class, Map<String, Class<? extends Browser>>> typeParameters =
        proposedParameters == null ?
      new HashMap<>() : proposedParameters; 

    Map<Class<?>, Type> superInerfacesWithTypeParams = new LinkedHashMap<>();

    {
      Class[] interfaces = inerface.getInterfaces();
      Type[] genericInterfaces = inerface.getGenericInterfaces();
      for (int i = 0;i < interfaces.length;i++) {
        Class proposedInterface = interfaces[i];
        if (proposedInterface.getTypeParameters().length > 0) {
          superInerfacesWithTypeParams.put(interfaces[i], genericInterfaces[i]);
        }
      }
    }

    superInerfacesWithTypeParams.forEach((clazz, type) -> {
      TypeVariable[] paramNames = clazz.getTypeParameters();
      Type[] paramArguments;

      try {
        paramArguments = ((ParameterizedType) type).getActualTypeArguments();
      } catch (ClassCastException e) {
        throw new PageInstantiationException(
            "Expected Type parameters were not given to " + clazz.getName() +
            " from " + inerface.getName()); 
      }

      Map<String, Class<? extends Browser>> typeArguments = new LinkedHashMap<>();
      StringBuilder nonPageTypeArguments = new StringBuilder();

      for (int i = 0; i < paramNames.length; i++) {
        TypeVariable paramName = paramNames[i];
        Type paramArgument = paramArguments[i];



        try {
          if (!Browser.class.isAssignableFrom((Class) paramArgument)) {
            throw new ClassCastException(); 
          }

          typeArguments.put(paramName.getName(),
              (Class<? extends Browser>) paramArgument);
        } catch(ClassCastException e) {
          Map<String, Class<? extends Browser>> preexistingParams =
              typeParameters.get(inerface);

          if (preexistingParams != null) {
            Class passedClass = preexistingParams.get(paramArgument.getTypeName());

            if (passedClass != null) {
              typeArguments.put(paramName.getName(), passedClass);
            }
          } else {
            if (nonPageTypeArguments.length() > 0) {
              nonPageTypeArguments.append(", ");
            }

            nonPageTypeArguments.append(paramArgument.getTypeName());
          }
        }
      }

      if (nonPageTypeArguments.length() > 0) {
        throw new PageInstantiationException(
            "Pages are the only allowed Type parameters." +
            "  The following Type parameters were given in " +
            inerface.getName() +
            ": " + nonPageTypeArguments);
      }
      typeParameters.put(clazz, typeArguments);
      getTypeArguments(clazz, typeParameters);
    });

    return typeParameters;
  }

  private static String expectedImplMethodSignature(Method method) {
    StringBuilder params = new StringBuilder();

    Arrays.asList(method.getParameterTypes()).forEach(clazz -> {
      if (params.length() > 0) {
        params.append(", ");
      }

      params.append(clazz.getSimpleName());
    });

    return "void " + method.getName() + "(" + params + ")";
  }
}