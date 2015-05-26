package com.github.jsdevel.testng.selenium;

import com.github.jsdevel.testng.selenium.exceptions.PageFactoryInstantiationException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

class PageFactoryProxyFactory {
  private static final Map<Class,
      Object> cachedPageFactoryProxies = new ConcurrentHashMap<>();

  static synchronized <PF extends PageFactory> PF getPageFactoryProxy(
      Class<PF> pageFactoryProxyClass, MethodContext context) {
    Object cachedPageFactoryProxy = cachedPageFactoryProxies.get(
        pageFactoryProxyClass);

    if (cachedPageFactoryProxy == null) {
      if (null == context) {
        throw new NullPointerException("MethodContext may not be null.");
      }

      validatePageFactoryProxy(pageFactoryProxyClass);

      registerPages(pageFactoryProxyClass);

      cachedPageFactoryProxy = Proxy.newProxyInstance(
          pageFactoryProxyClass.getClassLoader(),
          new Class[] {pageFactoryProxyClass}, new PageFactoryProxy(context));

      cachedPageFactoryProxies.put(pageFactoryProxyClass,
          cachedPageFactoryProxy);
    }

    return (PF) cachedPageFactoryProxy;
  }

  private static void registerPages(Class pageFactoryProxyClass) {
    Set<Class<? extends Browser>> allowedPages = new HashSet<>();

    Arrays.asList(pageFactoryProxyClass.getMethods()).forEach(method -> {
      allowedPages.add((Class<? extends Browser>)method.getReturnType());
    });

    Arrays.asList(pageFactoryProxyClass.getMethods()).forEach(method -> {
      PageProxyFactory.registerPage(
          (Class<? extends Browser>) method.getReturnType(),
              pageFactoryProxyClass);
    });
  }

  private static void validatePageFactoryProxy(Class pageFactoryProxyClass) {
    assertPageFactoryProxyClassIsInterface(pageFactoryProxyClass);
    assertPageFactoryProxyClassDoesNotHaveTypeParameters(pageFactoryProxyClass);
    assertPageFactoryProxyClassHasAtLeast1Method(pageFactoryProxyClass);
    assertPageFactoryProxyClassHasNoMethodWithTypeParameters(pageFactoryProxyClass);
    assertPageFactoryProxyClassMethodsReturnAssignablesOfPage(pageFactoryProxyClass);
    assertPageFactoryProxyClassMethodsAreProperlyNamed(pageFactoryProxyClass);
    assertPageFactoryProxyClassHasAtLeaseOneFactoryMethodWithNoParametersForEachPage(pageFactoryProxyClass);
    assertPageFactoryProxyClassMethodReturnTypesDoNotHaveTypeParameters(pageFactoryProxyClass);
    assertPageFactoryProxyClassMethodsReturnInterfaces(pageFactoryProxyClass);
    assertPageFactoryProxyClassMethodReturnTypesHaveImpls(pageFactoryProxyClass);
    assertPageFactoryProxyClassMethodParameterTypesAreHandled(pageFactoryProxyClass);
  }

  private static void assertPageFactoryProxyClassIsInterface(Class pageFactoryProxyClass) {
    if (!pageFactoryProxyClass.isInterface()) {
      throw new PageFactoryInstantiationException("Expected " +
          pageFactoryProxyClass.getName() + " to be an interface.");
    }
  }

  private static void assertPageFactoryProxyClassDoesNotHaveTypeParameters(Class pageFactoryProxyClass) {
    if (hasTypeParameters(pageFactoryProxyClass)) {
      throw new PageFactoryInstantiationException(
          "Type Parameters are not supported on PageFactories in " +
          pageFactoryProxyClass.getName());        
    }
  }

  private static void assertPageFactoryProxyClassHasAtLeast1Method(Class pageFactoryProxyClass) {
    if (pageFactoryProxyClass.getMethods().length == 0) {
      throw new PageFactoryInstantiationException(
          "PageFactories must declare at least 1 method." +
          "  0 methods declared in " + pageFactoryProxyClass.getName());
    }
  }

  private static void assertPageFactoryProxyClassMethodsReturnAssignablesOfPage(Class pageFactoryProxyClass) {
    StringBuilder methodsThatDoNotReturnPage = new StringBuilder();
    Arrays.asList(pageFactoryProxyClass.getMethods()).stream()
        .sorted((Method a, Method b) -> a.getName().compareTo(b.getName()))
        .forEach(method -> {
          Class<?> returnType = method.getReturnType();
          if (!Browser.class.isAssignableFrom(returnType)) {
            if (methodsThatDoNotReturnPage.length() > 0) {
              methodsThatDoNotReturnPage.append(", ");              
            }

            methodsThatDoNotReturnPage.append(method.getName());
          }
        });

    if (methodsThatDoNotReturnPage.length() > 0) {
      throw new PageFactoryInstantiationException(
          pageFactoryProxyClass.getName() +
          " contains the following methods that do not return instances" +
          " of Page: " + methodsThatDoNotReturnPage);
    }
  }

  private static void assertPageFactoryProxyClassHasAtLeaseOneFactoryMethodWithNoParametersForEachPage(Class pageFactoryProxyClass) {
    StringBuilder pagesThatHaveNoDefaultFactoryMethod = new StringBuilder();
    Arrays.asList(pageFactoryProxyClass.getMethods()).stream()
        .sorted((Method a, Method b) -> a.getName().compareTo(b.getName()))
        .forEach(method -> {
          String pageName = method.getReturnType().getSimpleName();
          try {
            pageFactoryProxyClass.getMethod("get" + pageName);
          } catch (NoSuchMethodException e) {
            if (pagesThatHaveNoDefaultFactoryMethod.length() > 0) {
              pagesThatHaveNoDefaultFactoryMethod.append(", ");
            }
            
            pagesThatHaveNoDefaultFactoryMethod.append(pageName);
          }
        });

    if (pagesThatHaveNoDefaultFactoryMethod.length() > 0) {
      throw new PageFactoryInstantiationException(
          "The following pages have no default factory method (no arguments) in " +
          pageFactoryProxyClass.getName() +
          ": " + pagesThatHaveNoDefaultFactoryMethod);
    }
  }

  private static void assertPageFactoryProxyClassHasNoMethodWithTypeParameters(Class pageFactoryProxyClass) {
    StringBuilder methodsThatAcceptTypeParameters = new StringBuilder();
    Arrays.asList(pageFactoryProxyClass.getMethods()).stream()
        .sorted((Method a, Method b) -> a.getName().compareTo(b.getName()))
        .forEach(method -> {
          if (method.getTypeParameters().length > 0) {
            if (methodsThatAcceptTypeParameters.length() > 0) {
              methodsThatAcceptTypeParameters.append(", ");
            }
            methodsThatAcceptTypeParameters.append(getMethodSignature(method));
          }
        });

    if (methodsThatAcceptTypeParameters.length() > 0) {
      throw new PageFactoryInstantiationException(
          "The following methods accept Type parameters in " +
          pageFactoryProxyClass.getName() +
          ": " + methodsThatAcceptTypeParameters);
    }
  }

  private static void assertPageFactoryProxyClassMethodsAreProperlyNamed(Class pageFactoryProxyClass) {
    StringBuilder methodsThatAreNotProperlyNamed = new StringBuilder();
    Arrays.asList(pageFactoryProxyClass.getMethods()).stream()
        .sorted((Method a, Method b) -> a.getName().compareTo(b.getName()))
        .forEach(method -> {
          String pageName = method.getReturnType().getSimpleName();

          if (!("get" + pageName).equals(method.getName())) {
            if (methodsThatAreNotProperlyNamed.length() > 0) {
              methodsThatAreNotProperlyNamed.append(", ");              
            }

            methodsThatAreNotProperlyNamed.append(method.getName());
          }
        });

    if (methodsThatAreNotProperlyNamed.length() > 0) {
      throw new PageFactoryInstantiationException(
          "The following methods in " + pageFactoryProxyClass.getName() +
          " were not named the name of the page with a prefix of get: " +
          methodsThatAreNotProperlyNamed);
    }
  }

  private static void assertPageFactoryProxyClassMethodReturnTypesDoNotHaveTypeParameters(Class pageFactoryProxyClass) {
    StringBuilder pagesThatUseTypeParameters = new StringBuilder();
    Set<Class> alreadyProcessed = new HashSet<>();
    Arrays.asList(pageFactoryProxyClass.getMethods()).stream()
        .sorted((Method a, Method b) -> a.getName().compareTo(b.getName()))
        .forEach(method -> {
          Class returnType = method.getReturnType();

          if (alreadyProcessed.contains(returnType)) {
            return; 
          }

          if (hasTypeParameters(returnType)) {
            if (pagesThatUseTypeParameters.length() > 0) {
              pagesThatUseTypeParameters.append(", ");              
            }

            pagesThatUseTypeParameters.append(returnType.getName());
            alreadyProcessed.add(returnType);
          }
        });

    if (pagesThatUseTypeParameters.length() > 0) {
      throw new PageFactoryInstantiationException(
          "Pages are not allowed to accept Type Parameters." +
          "  The following Pages use them: " +
          pagesThatUseTypeParameters);
    }
  }

  private static void assertPageFactoryProxyClassMethodsReturnInterfaces(Class pageFactoryProxyClass) {
    StringBuilder methodsThatDoNotReturnInterfaces = new StringBuilder();
    List<Method> methods = Arrays.asList(pageFactoryProxyClass.getMethods());

    methods.stream()
        .sorted((Method a, Method b) -> a.getName().compareTo(b.getName()))
        .forEach(method -> {
          if (!method.getReturnType().isInterface()) {
            if (methodsThatDoNotReturnInterfaces.length() > 0) {
              methodsThatDoNotReturnInterfaces.append(", ");              
            }

            methodsThatDoNotReturnInterfaces.append(method.getName());
          }
        });

    if (methodsThatDoNotReturnInterfaces.length() > 0) {
      throw new PageFactoryInstantiationException(
          "The following methods return non interfaces in " +
          pageFactoryProxyClass.getName() +
          ": " + methodsThatDoNotReturnInterfaces);
    }
  }

  private static void assertPageFactoryProxyClassMethodReturnTypesHaveImpls(Class pageFactoryProxyClass) {
    StringBuilder pagesThatHaveNoImpls = new StringBuilder();
    Set<Class> alreadyProcessed = new HashSet();
    Arrays.asList(pageFactoryProxyClass.getMethods()).stream()
        .sorted((Method a, Method b) -> a.getName().compareTo(b.getName()))
        .forEach(method -> {
          Class<?> returnType = method.getReturnType();

          if (alreadyProcessed.contains(returnType)) {
            return; 
          }

          if (!hasImpl(returnType)) {
            if (pagesThatHaveNoImpls.length() > 0) {
              pagesThatHaveNoImpls.append(", ");              
            }

            pagesThatHaveNoImpls.append(method.getReturnType().getName());
            alreadyProcessed.add(returnType);
          }
        });

    if (pagesThatHaveNoImpls.length() > 0) {
      throw new PageFactoryInstantiationException(
          "No implementations were found for the following pages in " +
          pageFactoryProxyClass.getName() +
          ": " + pagesThatHaveNoImpls);
    }
  }

  private static void assertPageFactoryProxyClassMethodParameterTypesAreHandled(Class pageFactoryProxyClass) {
    StringBuilder methodsWithUnHandledParameterTypes = new StringBuilder();
    List<Method> methods = Arrays.asList(pageFactoryProxyClass.getMethods());

    methods.stream()
        .sorted((Method a, Method b) -> a.getName().compareTo(b.getName()))
        .forEach(method -> {
          if (method.getParameterCount() > 0 && hasUnHandledParamterTypes(method)) {
            if (methodsWithUnHandledParameterTypes.length() > 0) {
              methodsWithUnHandledParameterTypes.append(", ");              
            }

            methodsWithUnHandledParameterTypes.append(method.getName());
          }
        });

    if (methodsWithUnHandledParameterTypes.length() > 0) {
      throw new PageFactoryInstantiationException(
          "Unhandled parameter types in " +
          pageFactoryProxyClass.getName() +
          " for the following methods: " + methodsWithUnHandledParameterTypes);
    }
  }

  private static boolean hasTypeParameters(Class clazz) {
    return clazz.getTypeParameters().length > 0;
  }
  private static boolean hasImpl(Class clazz) {
    String name = clazz.getName();
    String implName = name + "Impl";
    try {
      clazz.getClassLoader().loadClass(implName);
      return true;
    } catch (ClassNotFoundException ex) {
      return false;
    }
  }

  private static String getMethodSignature(Method method) {
    StringBuilder params = new StringBuilder();

    Arrays.asList(method.getParameterTypes()).forEach(clazz -> {
      if (params.length() > 0) {
        params.append(", ");
      }

      params.append(clazz.getSimpleName());
    });

    return method.getName() + "(" + params + ")";
  }

  private static boolean hasUnHandledParamterTypes(Method method) {
    return Arrays.asList(method.getParameterTypes()).stream()
        .filter(clazz -> !clazz.equals(String.class))
        .toArray().length > 0;
  }
}