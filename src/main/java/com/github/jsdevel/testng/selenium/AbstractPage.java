package com.github.jsdevel.testng.selenium;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.WebDriver;
import static org.testng.AssertJUnit.assertTrue;

/**
 * A Page that can be configured by AbstractPageFactory.
 * 
 * @author Joe Spencer
 * @param <P> The Page that extends this.
 * 
 */
public abstract class AbstractPage<P extends AbstractPage> implements Page<P>,
                                                                      PageAssertions<P> {
  protected WebDriver webDriver;
  protected MethodContext context;

  @Override
  public P assertCurrentUrlPathStartsWith(String proposal) {
    context.log("Asserting that the current URL path starts with " + proposal);
    String currentWebDriverUrl = webDriver.getCurrentUrl();
    context.log("The current WebDriver URL is " + currentWebDriverUrl);
    URL currentUrl;
    try {
      currentUrl = new URL(currentWebDriverUrl);
    } catch (MalformedURLException e) {
      throw new IllegalStateException(e);
    }
    context.log("The current WebDriver URL path is " + currentUrl.getPath());

    assertTrue(currentUrl.getPath().indexOf(proposal) == 0);
    return (P) this;
  }

  @Override
  public P handleInitialPageLoad() {
    return (P) this;
  }

  // Used by AbstractPageFactory.
  void setMethodContext(MethodContext context) {
    this.context = context;
  }

  // Used by AbstractPageFactory.
  void setWebDriver(WebDriver webDriver) {
    this.webDriver = webDriver;
  }
}
