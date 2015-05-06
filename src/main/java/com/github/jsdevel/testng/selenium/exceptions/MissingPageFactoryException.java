package com.github.jsdevel.testng.selenium.exceptions;

/**
 * Thrown to indicate that a Method annotated with @Test was declared in a class
 * that was missing PageFactory.
 * 
 * @author Joe Spencer
 */
public class MissingPageFactoryException extends RuntimeException {
  public MissingPageFactoryException(String message) {
    super(message);
  }
}
