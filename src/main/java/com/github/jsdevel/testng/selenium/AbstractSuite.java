package com.github.jsdevel.testng.selenium;

import java.lang.reflect.Method;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * An AbstractSuite that is thread safe, allows methods to be run in
 * parallel, and instantiates relevant test Types such as AbstractPageFactory
 * and WebDriver.
 * 
 * @author Joe Spencer
 * @param <PF> The PageFactory type used to instantiate Pages during tests.
 */
public class AbstractSuite<PF extends AbstractPageFactory> {
  private final ThreadLocal<MethodContext<PF>> methodContext = new ThreadLocal();

  protected PF getPageFactory() {
    return methodContext.get().getPageFactory();
  }

  @BeforeMethod(alwaysRun = true)
  public void beforeMethod(Method method) {
    MethodContext<PF> context = new MethodContext(method);

    AbsractSuiteHelpers.addWebDriver(context);
    AbsractSuiteHelpers.<PF>addPageFactory(context);

    methodContext.set(context);
  }

  @AfterMethod(alwaysRun = true)
  public void afterMethod() {
    MethodContext<PF> context = methodContext.get();

    if (context == null) {
      return; 
    }

    if (context.getOutput() != null) {
      for (String line: context.getOutput()) {
        TestNGSeleniumLogger.log(line);
      }
    }

    methodContext.remove();
  }
}