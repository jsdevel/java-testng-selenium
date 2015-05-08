package com.github.jsdevel.testng.selenium.exceptions;

/**
 * Thrown to indicate that a {@link java.lang.reflect.Method} annotated with
 * {@link org.testng.annotations.Test} was declared in a class that was missing
 * {@link com.github.jsdevel.testng.selenium.PageFactory}.
 * 
 * @author Joe Spencer
 */
public class MissingPageFactoryException extends RuntimeException {
  public MissingPageFactoryException(String message) {
    super(message);
  }
}
