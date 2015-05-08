package com.github.jsdevel.testng.selenium;

/**
 * A PageFactory that is configurable by {@link AbstractSuite}s.
 * 
 * Defining methods that take multiple {@link String} arguments is prohibited,
 * as any {@link String} argument provided will be used as the desired URL when
 * initializing the desired Page.
 * 
 * All methods of this PageFactory MUST return sub classes of {@link Page}.
 * 
 * @author Joe Spencer
 */
public interface PageFactory {
  
}
