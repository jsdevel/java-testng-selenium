package com.github.jsdevel.testng.selenium.config;

/**
 * Constants representing default values for EnvironmentConfig.
 * 
 * @see Config
 * 
 * @author Joe Spencer
 */
public class ConfigDefaults {
  /**
   * String value of "<code>false</code>".
   */
  public static final String DEBUG = "disabled";

  /**
   * String value of "<code>PhantomJS</code>".
   */
  public static final String DRIVER = "PhantomJS";

  /**
   * No default endpoint is provided.
   */
  public static final String ENDPOINT = null;

  /**
   * String value of "<code>TestNG-Selenium</code>".
   */
  public static final String LOGGING_PREFIX = "TestNG-Selenium";

  /**
   * String value of "<code>Phone</code>".
   */
  public static final String SCREENSIZE = "Phone";

  /**
   * Value of <code>System.getProperty("java.io.tmpdir")</code>.
   */
  public static final String TMPDIR = System.getProperty("java.io.tmpdir");
}
