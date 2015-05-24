package com.github.jsdevel.testng.selenium;

import com.github.jsdevel.testng.selenium.exceptions.PageInitializationException;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.WebDriver;

/**
 * An implementation of {@link OldPage} that is configured and initialized by
 * {@link AbstractSuite}.
 * 
 * 
 * @param <P> The AbstractPage that extends this.
 * @param <PF> The PageFactory that build this OldPage.
 * @see OldPage
 * @author Joe Spencer
 * 
 */
public abstract class AbstractPage<P extends AbstractPage,
                                   PF extends PageFactory> implements OldPage<P, PF> {
  private MethodContext context;
  private String endpoint;
  private URL initialUrl;
  private PF pageFactory;
  private WebDriver webDriver;

  @Override
  public final MethodContext getContext() {
    return context;
  }

  @Override
  public final String getEndpoint() {
    return this.endpoint;
  }

  @Override
  public final URL getInitialUrl() {
    return initialUrl;
  }

  @Override
  public final OldPage<P, PF> getPage() {
    return this;
  }

  @Override
  public final PF getPageFactory() {
    return pageFactory;
  }
 
  @Override
  public final WebDriver getWebDriver() {
    return webDriver;
  }

  /**
   * Allows the page to signal whether or not it can be viewed from the provided
   * URL.  This method is called during the initialization phase.  Override as
   * needed for page specific validation.
   * 
   * @param proposedUrl The proposed URL.
   * @return whether or not this page can be viewed by the proposedUrl.
   */
  protected boolean isPageViewableFrom(URL proposedUrl) {
    return true;
  }

  /**
   * Called when this page is initialized.  An initialized page may not be fully
   * loaded in the {@link org.openqa.selenium.WebDriver}.  You may override this
   * method to perform actions like waiting for requests to finish, or elements
   * to be visible etc.
   */
  protected void handlePageInitialized() {
    // do nothing by default
  }

  /**
   * Initializes this OldPage.  This is an internal operation, not meant to be
   * exposed outside of testng-selenium.
   * The initialization process is as follows:
   * 
   * <ul>
   * <li>Set internal values, such as context, endpoint, url etc.</li>
   * <li>Validate the syntax of the desired {@link URL}.</li>
   * <li>If the {@link WebDriver} associated with this page is currently viewing
   * about:blank (normal when a WebDriver is first instantiated), then it loads
   * the desired URL.</li>
   * <li>Initializes the page with
   * {@link org.openqa.selenium.support.PageFactory}.</li>
   * <li>Calls {@link #handlePageInitialized()} to optionally do something once
   * the AbstractPage has been initialized.  This would be a good time to wait
   * for the page to get fully setup.</li>
   * <li>Calls {@link #isPageViewableFrom(java.net.URL)} to verify that the
   * desired URL represents this {@link OldPage}.</li>
   * </ul>
   * 
   * @param desiredUrl
   * @param context 
   * 
   * @throws PageInitializationException If at any point during page
   * initialization the URL in the WebDriver is illegal or isn't viewable by
   * calling isPageViewableFrom.
   */
  void initialize(String desiredUrl, MethodContext<PF> context) {
    this.context = context;
    this.endpoint = context.getEndpoint();
    this.pageFactory = context.getPageFactory();
    this.webDriver = context.getWebDriver();

    this.initialUrl = getUrl(ofAbsoluteUrl(desiredUrl, endpoint));

    context.log("Initializing " + this.getClass().getSimpleName());

    if (this.webDriver.getCurrentUrl().equals("about:blank")) {
      context.log("Detected about:blank.");
      context.log("Navigating to " + this.initialUrl);
      this.webDriver.get(initialUrl.toString());
    }

    context.log("Instantiating any WebElement or List<WebElement> fields " +
      "with org.openqa.selenium.support.PageFactory");
    org.openqa.selenium.support.PageFactory.initElements(context.getWebDriver(),
        this);

    context.log("Page initialized.  Calling handlePageInitialized().");
    this.handlePageInitialized();

    this.initialUrl = getUrl(webDriver.getCurrentUrl());

    if (!this.isPageViewableFrom(this.initialUrl)) {
      context.log(this.getClass().getName() + " was not viewable from " +
          this.initialUrl.toString() + ".  Throwing an exception.");
      throw new PageInitializationException(this.getClass().getName() +
          " can not be viewed from " + this.initialUrl.toString());
    }
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

  private URL getUrl(String url) {
    try {
      return new URL(url);
    } catch (MalformedURLException e) {
      throw new PageInitializationException(e);
    }
  }
}
