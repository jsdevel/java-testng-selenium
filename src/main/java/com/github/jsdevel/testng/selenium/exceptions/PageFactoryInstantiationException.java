package com.github.jsdevel.testng.selenium.exceptions;

/**
 * Thrown during page factory proxy creation.
 * 
 * @author Joe Spencer
 */
public class PageFactoryInstantiationException extends RuntimeException {
  public PageFactoryInstantiationException(String message) {
    super(message);
  }

  public PageFactoryInstantiationException(Throwable cause) {
    super(cause);
  }
}