package com.github.jsdevel.testng.selenium;

import org.openqa.selenium.WebDriver;

/**
 * A context created for each test run.
 * 
 * @author Joe Spencer
 * @param <PF> The {@link PageFactory} configured by {@link AbstractSuite}.
 */
public interface MethodContext<PF extends PageFactory> {
  /**
   * Returns the configured endpoint for this test run.
   * 
   * @see com.github.jsdevel.testng.selenium.environment.EnvironmentConfigDefaults
   * @return The configured endpoint.
   */
  String getEndpoint();

  /**
   * Returns the configured {@link PageFactory} for this test run.
   * 
   * @return The configured {@link PageFactory}.
   */
  PF getPageFactory();

  /**
   * Returns the screen size that has been configured for this test run.
   * 
   * @return The configured screen size.
   */
  Object getScreensize();

  /**
   * Returns the configured {@link WebDriver} for this test run.
   * @see com.github.jsdevel.testng.selenium.environment.EnvironmentConfigDefaults
   * @see com.github.jsdevel.testng.selenium.annotations.drivers
   * 
   * @return The configured driver;
   */
  WebDriver getWebDriver();

  /**
   * Returns the configured
   * {@link com.github.jsdevel.testng.selenium.annotations.driverconfig.UserAgent}
   * for this test run.
   * 
   * @see com.github.jsdevel.testng.selenium.environment.EnvironmentConfigDefaults
   * @see com.github.jsdevel.testng.selenium.annotations.driverconfig.UserAgent
   * 
   * @return The configured UserAgent String.
   */
  String getUserAgent();

  /**
   * Logs a message for future processing.
   * 
   * @param msg The message to log.
   */
  void log(String msg);
}
