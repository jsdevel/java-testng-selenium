package com.github.jsdevel.testng.selenium;

import com.github.jsdevel.testng.selenium.exceptions.PageInstantiationException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class PageFactoryProxy implements InvocationHandler {
  private final MethodContext context;

  public PageFactoryProxy(MethodContext context) {
    this.context = context;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Object invoke(Object proxy, Method pageFactoryMethod, Object[] args) throws Throwable {
    Object page = pageFactoryMethod.getReturnType().newInstance();

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

    List<String> stringArgs = new ArrayList<>();
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
