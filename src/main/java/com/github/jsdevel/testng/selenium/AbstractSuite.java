package com.github.jsdevel.testng.selenium;

import java.lang.reflect.Method;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * An AbstractSuite that is thread safe and allows methods to be run in
 * parallel.
 * 
 * @author Joe Spencer
 * @param <PF> The PageFactory type.
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
    for (String line: context.getOutput()) {
      TestNGSeleniumLogger.log(line);
    }

    methodContext.remove();
  }
}