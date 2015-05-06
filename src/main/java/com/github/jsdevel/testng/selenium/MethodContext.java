package com.github.jsdevel.testng.selenium;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.openqa.selenium.WebDriver;

/**
 * A context created for each test run.
 * 
 * @author Joe Spencer
 * @param <PF> The PageFactory configured.
 */
public class MethodContext<PF> {
  final Method method;
  private PF pageFactory;
  private WebDriver webDriver;
  private final List<String> output;

  public MethodContext(Method method) {
    this.method = method;
    this.output = new ArrayList();
  }

  public WebDriver getWebDriver() {
    return this.webDriver;
  }

  public PF getPageFactory() {
    return this.pageFactory;
  }

  public void log(String msg) {
    this.output.add(msg);
  }

  // Used by AbstractSuite.
  List<String> getOutput() {
    return Collections.unmodifiableList(output);
  }

  // Used by AbstractSuite.
  void setPageFactory(PF pageFactory) {
    this.pageFactory = pageFactory; 
  }

  // Used by AbstractSuite.
  void setWebDriver(WebDriver webDriver) {
    this.webDriver = webDriver;
  }
}
