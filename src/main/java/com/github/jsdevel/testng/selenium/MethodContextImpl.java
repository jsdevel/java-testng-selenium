package com.github.jsdevel.testng.selenium;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.openqa.selenium.WebDriver;


class MethodContextImpl<PF extends PageFactory> implements MethodContext<PF> {
  final Method method;
  private PF pageFactory;
  private WebDriver webDriver;
  private final List<String> output;
  private String userAgent;
  private String endpoint;

  /**
   * Constructs this context with the {@link Method} representing the current
   * TestNG test run.
   * 
   * @param method The current test method being run.
   */
  public MethodContextImpl(Method method) {
    this.method = method;
    this.output = new ArrayList();
  }

  @Override
  public String getEndpoint() {
    return endpoint;
  }

  @Override
  public PF getPageFactory() {
    return this.pageFactory;
  }

  @Override
  public WebDriver getWebDriver() {
    return this.webDriver;
  }

  @Override
  public String getUserAgent() {
    return this.userAgent;
  }

  @Override
  public void log(String msg) {
    // System.out.println(msg);
    this.output.add(msg);
  }

  List<String> getOutput() {
    return Collections.unmodifiableList(output);
  }

  void setEndpoint(String endpoint) {
    this.endpoint = endpoint; 
  }
  void setPageFactory(PF pageFactory) {
    this.pageFactory = pageFactory; 
  }

  void setWebDriver(WebDriver webDriver) {
    this.webDriver = webDriver;
  }

  void setUserAgent(String userAgent) {
    this.userAgent = userAgent; 
  }
}
