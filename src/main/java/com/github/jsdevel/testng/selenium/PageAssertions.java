package com.github.jsdevel.testng.selenium;

/**
 * General assertions that apply to a Page.
 * 
 * @author Joe Spencer
 * @param <P> This Page.
 */
public interface PageAssertions<P extends Page> {
  /**
   * Asserts that the WebDriver's current URL path starts with the proposal.
   * @param proposal proposal
   * @return this Page.
   */
  P assertCurrentUrlPathStartsWith(String proposal);
}
