package com.github.jsdevel.testng.selenium.exceptions;

/**
 * Thrown during {@link com.github.jsdevel.testng.selenium.Page} instantiation
 * if something went wrong.
 * 
 * @author Joe Spencer
 */
public class PageInstantiationException extends RuntimeException {
  public PageInstantiationException(String message) {
    super(message);
  }

  public PageInstantiationException(Throwable cause) {
    super(cause);
  }
}