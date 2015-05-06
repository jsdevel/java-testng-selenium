package com.github.jsdevel.testng.selenium;

/**
 * A logger that prefixes output with TestNG-Selenium.
 * 
 * @author Joe Spencer
 */
public class TestNGSeleniumLogger {
  public static void log(String msg) {
    System.out.println("[" + EnvironmentConfig.LOGGING_PREFIX + "] " + msg);
  } 
}
