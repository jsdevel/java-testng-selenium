package com.github.jsdevel.testng.selenium;

import org.openqa.selenium.WebDriver;

/**
 * Represents a page factory.
 * 
 * @author Joe Spencer
 */
public class AbstractPageFactory {
  private String endpoint;
  private WebDriver webDriver;
  private MethodContext context;

  protected String getEndpoint() {
    return endpoint;
  }

  protected WebDriver getWebDriver() {
    return webDriver;
  }

  protected <P extends AbstractPage> P initializePage(String desiredUrl, P page) {
    String proposedUrl = endpoint + desiredUrl;
    context.log("Initializing a[n] " + page.getClass().getSimpleName());
    page.setMethodContext(context);
    page.setWebDriver(webDriver);

    if (webDriver.getCurrentUrl().equals("about:blank")) {
      context.log("Detected about:blank.");
      context.log("Navigating to " + proposedUrl);
      context.log("Handling initial Page load.");
      webDriver.get(proposedUrl);
      page.handleInitialPageLoad();
    }

    context.log("Asserting that the page is Viewable from " + proposedUrl);
    page.assertPageIsViewableFrom(proposedUrl);
    return page;
  }

  // To be used by AbstractSuite .
  void setEndoint(String endpoint) {
    this.endpoint = endpoint;
  }

  // To be used by AbstractSuite .
  void setMethodContext(MethodContext context) {
    this.context = context;
  }

  // To be used by AbstractSuite .
  void setWebDriver(WebDriver webDriver) {
    this.webDriver = webDriver;
  }
}