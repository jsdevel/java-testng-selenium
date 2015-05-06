package com.github.jsdevel.testng.selenium;

/**
 * A Page that can be configured by an AbstractPageFactory.
 * 
 * @author Joe Spencer
 * @param <P> The Page that implements this interface.
 */
public interface Page<P extends Page> {
  /**
   * Asserts that the Page can be viewed from the proposedUrl.
   * 
   * @param proposedUrl A proposed URL.
   * @throws IllegalStateException If the Page can't be viewed from the proposed
   *         URL.
   * 
   * @return This Page.
   */
  P assertPageIsViewableFrom(String proposedUrl);

  /**
   * Called when the Page is initially loaded in the WebDriver.
   * 
   * @return This Page.
   */
  P handleInitialPageLoad();
}
