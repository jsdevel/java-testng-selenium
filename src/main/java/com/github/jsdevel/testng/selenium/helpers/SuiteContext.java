package com.github.jsdevel.testng.selenium.helpers;

import java.lang.reflect.Method;

/**
 * A SuiteContext created for each test run.
 * 
 * @author Joe Spencer
 */
public class SuiteContext {
  private Method method;

  public void setMethod(Method method) {
    if (this.method == null) {
      this.method = method; 
    }
  }
}
