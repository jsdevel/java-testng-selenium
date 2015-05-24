package com.github.jsdevel.testng.selenium;

import java.net.URL;
import org.openqa.selenium.WebDriver;

/**
 * Represents a page that has been configured.
 * 
 * @param <P> The configured OldPage.
 * @param <PF> The PageFactory that build this page.
 * @see AbstractPage
 * @author Joe Spencer
 */
public interface OldPage<P extends OldPage, PF extends PageFactory> {
  /**
   * Returns the context of the Method annotated with
   * {@link org.testng.annotations.Test}.
   * 
   * @return The method context.
   */
  MethodContext getContext();

  /**
   * Returns the endpoint that was configured for the current test run.
   * 
   * @return The endpoint.
   */
  String getEndpoint();

  /**
   * Returns the URL that was used during page initialization.
   * 
   * @return The initial URL.
   */
  URL getInitialUrl();

  /**
   * Returns this OldPage.
   * 
   * @return This OldPage.
   */
  OldPage<P, PF> getPage();

  /**
   * The PageFactory instance that built this OldPage.
   * 
   * @return  The PageFactory that built this OldPage.
   */
  PF getPageFactory();

  /**
   * Returns the {@link org.openqa.selenium.WebDriver} that has been configured
   * for this test run.
   * 
   * @return The {@link org.openqa.selenium.WebDriver}.
   */
  WebDriver getWebDriver(); 
}
