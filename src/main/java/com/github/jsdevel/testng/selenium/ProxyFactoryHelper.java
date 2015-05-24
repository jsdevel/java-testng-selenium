package com.github.jsdevel.testng.selenium;

import java.lang.reflect.Method;

class ProxyFactoryHelper {
  static boolean hasImpl(Class clazz) {
    String name = clazz.getName();
    String implName = name + "Impl";
    try {
      clazz.getClassLoader().loadClass(implName);
      return true;
    } catch (ClassNotFoundException ex) {
      return false;
    }
  }

  private static boolean isGeneric(Method method) {
    return !method.getGenericReturnType().getTypeName()
        .equals(method.getReturnType().getTypeName());
  }  
}
