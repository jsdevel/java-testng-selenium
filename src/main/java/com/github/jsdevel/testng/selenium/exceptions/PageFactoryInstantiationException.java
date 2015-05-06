package com.github.jsdevel.testng.selenium.exceptions;

/**
 * Thrown when instantiating the AbstractPageFactory specified with
 * PageFactory.
 * 
 * @author Joe Spencer
 */
public class PageFactoryInstantiationException extends RuntimeException {
  public PageFactoryInstantiationException(Throwable cause) {
    super(cause);
  }
}
