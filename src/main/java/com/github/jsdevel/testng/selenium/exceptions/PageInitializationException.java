package com.github.jsdevel.testng.selenium.exceptions;

/**
 * Thrown during {@link com.github.jsdevel.testng.selenium.Page} initialization
 * if something went wrong.
 * 
 * @author Joe Spencer
 */
public class PageInitializationException extends RuntimeException {
  public PageInitializationException(String message) {
    super(message);
  }

  public PageInitializationException(Throwable cause) {
    super(cause);
  }
}