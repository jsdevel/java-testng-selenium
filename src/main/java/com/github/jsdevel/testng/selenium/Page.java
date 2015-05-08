package com.github.jsdevel.testng.selenium;

import com.github.jsdevel.testng.selenium.exceptions.PageInstantiationException;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.WebDriver;
import static org.testng.AssertJUnit.assertTrue;

/**
 * A Page that can be configured and initialized.
 * 
 * The initialization process is as follows:
 * <ul>
 * <li>Set internal values, such as context, endpoint, url etc.</li>
 * <li>Validate the desired {@link URL}.</li>
 * <li>If the {@link WebDriver} associated with this page is currently viewing
 * about:blank (normal when a WebDriver is first instantiated), then it loads
 * the desired URL.</li>
 * <li>Calls {@link #assertPageIsViewableFrom(java.net.URL)} to verify that the
 * desired URL represents this {@link Page}.</li>
 * <li>Calls {@link #handlePageLoaded()} to optionally do something once the
 * Page has been initialized.</li>
 * </ul>
 * 
 * @author Joe Spencer
 * @param <P> The Page that extends this.
 * 
 */
public abstract class Page<P extends Page> implements PageAssertions<P> {
  protected MethodContext context;
  protected String endpoint;
  protected URL url;
  protected WebDriver webDriver;

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

  /**
   * Asserts that this page can be viewed from the proposedUrl.  This is called
   * during the initialization period of this page.
   * 
   * @param proposedUrl A proposed URL.
   * @throws IllegalStateException If this page can't be viewed from the
   * proposed URL.
   * 
   * @return Whether or not this page can be viewed from the proposed URL.
   */
  protected abstract boolean assertPageIsViewableFrom(URL proposedUrl);

  /**
   * Called when this page is loaded in the WebDriver and initialized.
   */
  protected void handlePageLoaded() {
    // do nothing by default
  }

  // START INTERNAL METHODS
  void initialize(String desiredUrl, MethodContext context) {
    this.context = context;
    this.endpoint = context.getEndpoint();
    this.webDriver = context.getWebDriver();

    URL proposedUrl;
    try {
      proposedUrl = new URL(ofAbsoluteUrl(desiredUrl, endpoint));
    } catch (MalformedURLException e) {
      throw new PageInstantiationException(e);
    }

    this.url = proposedUrl;

    context.log("Initializing " + this.getClass().getSimpleName());

    if (this.webDriver.getCurrentUrl().equals("about:blank")) {
      context.log("Detected about:blank.");
      context.log("Navigating to " + proposedUrl);
      this.webDriver.get(proposedUrl.toString());
    }

    context.log("Asserting that the page is Viewable from " + proposedUrl);
    this.assertPageIsViewableFrom(proposedUrl);

    context.log("Emitting Page Loaded Event.");
    this.handlePageLoaded();
  }

  private String ofAbsoluteUrl(String proposedUrl, String defaultProtocolHost) {
    if (proposedUrl == null) {
      return defaultProtocolHost; 
    }

    if (proposedUrl.matches("^[a-zA-Z]+://.+")) {
      return proposedUrl; 
    }

    return defaultProtocolHost + "/" + proposedUrl.replaceFirst("^/+", "");
  }
}
