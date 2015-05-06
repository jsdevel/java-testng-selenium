package com.github.jsdevel.testng.selenium.helpers;

import java.lang.reflect.Method;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * An AbstractSuite that is thread safe and allows methods to be run in
 * parallel.
 * 
 * @author Joe Spencer
 */
public class AbstractSuite {
  private final ThreadLocal<SuiteContext> suiteContext = new ThreadLocal();

  @BeforeMethod(alwaysRun = true)
  public void beforeMethod(Method method) {
    SuiteContext context = new SuiteContext();
    suiteContext.set(context);
    context.setMethod(method);
  }

  @AfterMethod(alwaysRun = true)
  public void afterMethod() {
    suiteContext.remove();
  }
}