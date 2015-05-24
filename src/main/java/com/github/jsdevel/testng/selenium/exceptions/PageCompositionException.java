package com.github.jsdevel.testng.selenium.exceptions;

/**
 * Thrown during page proxy creation.
 * 
 * @author Joe Spencer
 */
public class PageCompositionException extends RuntimeException {
  public PageCompositionException(String message) {
    super(message);
  }

  public PageCompositionException(Throwable cause) {
    super(cause);
  }
}