package com.github.jsdevel.testng.selenium;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

class PageProxy implements InvocationHandler {
  private final Class<? extends Browser> currentPageInterface;
  private final Map<Method, Object> implClasses;
  private final Map<Method, Method> methodImpls;
  private final Map<Method, Class<? extends Browser>> returnedPages;

  public PageProxy(Class<? extends Browser> currentPageInterface, Map<Method, Object> implClasses,
      Map<Method, Method> methodImpls,
      Map<Method, Class<? extends Browser>> returnedPages) {
    this.currentPageInterface = currentPageInterface;
    this.implClasses = implClasses;
    this.methodImpls = methodImpls;
    this.returnedPages = returnedPages;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    Method implementedMethod = methodImpls.get(method);
    Object implementation = implClasses.get(implementedMethod);
    implementedMethod.invoke(implementation, args);

    Class<? extends Browser> pageToReturn = returnedPages.get(method);

    if (pageToReturn != currentPageInterface) {
      return PageProxyFactory.getPageProxy(pageToReturn);
    }

    return currentPageInterface;
  }

  static class Builder {
    Class<? extends Browser> currentPageInterface;
    Map<Method, Class> implClasses;
    Map<Method, Method> methodImpls;
    Map<Method, Class<? extends Browser>> returnedPages;

    PageProxy build() {
      return new PageProxy(currentPageInterface, /*TODO*/ null,
          methodImpls, returnedPages);
    }
  }

  private static boolean isGeneric(Method method) {
    return !method.getGenericReturnType().getTypeName()
        .equals(method.getReturnType().getTypeName());
  }
}