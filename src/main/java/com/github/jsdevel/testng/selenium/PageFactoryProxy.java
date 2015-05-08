package com.github.jsdevel.testng.selenium;

import com.github.jsdevel.testng.selenium.exceptions.PageInstantiationException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * A Proxy implementation of an interface that extends PageFactory.  All methods
 of the sub interface must have a return type that directly extends
 Page.  All pages returned from this PageFactory will be initialized.
 * 
 * @author Joe Spencer
 * @param <PF> The PageFactory this Proxy wraps.
 */
class PageFactoryProxy<PF extends PageFactory> implements InvocationHandler {
  private final MethodContext context;
  private final Class<PF> pageFactoryClass;

  PageFactoryProxy(Class<PF> pageFactoryClass, MethodContext context) {
    this.context = context;
    this.pageFactoryClass = pageFactoryClass;
  }

  static <PF> PF newInstance(Class<PF> pageFactoryClass, MethodContext context) {
    return (PF) Proxy.newProxyInstance(pageFactoryClass.getClassLoader(),
        new Class[] {pageFactoryClass}, new PageFactoryProxy(pageFactoryClass,
            context));
  }

  @Override
  public Object invoke(Object proxy, Method pageFactoryMethod, Object[] args) throws Throwable {
    Object page = pageFactoryMethod.getReturnType().newInstance();

    if (page instanceof Page) {
      Page abstractPage = (Page) page;
      abstractPage.initialize(getDesiredUrl(pageFactoryMethod, args), context);
    } else {
      throw new PageInstantiationException("Pages returned from " +
          pageFactoryClass.getName() + " must return instances of " +
          Page.class.getName());
    }

    return page;
  }
  
  /**
   * Return the desired URL from the arguments passed to the PageFactory method.
   * The first String argument found is inferred to be the desired URL.  If
   * multiple String arguments given, then an Exception is thrown with the
   * location of the method in the message.
   * 
   * @param method Used then throwing an exception to inform the user where
   * multiple String arguments were given.
   * @param args
   * @throws PageInstantiationException If multiple String arguments were given.
   * @return the desired URL.
   */
  private String getDesiredUrl(Method method, Object[] args) {
    if (args == null || args.length == 0) {
      return "";
    }

    List<String> stringArgs = new ArrayList();
    for (Object arg: args) {
      if (arg instanceof String) {
        stringArgs.add((String) arg); 
      }
    }

    switch (stringArgs.size()) {
      case 0:
        return "";
      case 1:
        return stringArgs.get(0) == null ? "" : stringArgs.get(0);
      default:
        throw new PageInstantiationException(
            "Multiple String arguments are not allowed in " + method.getName());
    }
  }
}
