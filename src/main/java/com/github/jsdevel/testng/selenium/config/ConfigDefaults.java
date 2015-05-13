package com.github.jsdevel.testng.selenium.config;

/**
 * Constants representing default configuration values.
 *
 * @see Config
 *
 * @author Joe Spencer
 */
class ConfigDefaults {

  /**
   * String value of "<code>false</code>".
   */
  static final String DEBUG = "disabled";

  /**
   * String value of "<code>PhantomJS</code>".
   */
  static final String DRIVER = "PhantomJS";

  /**
   * No default endpoint is provided.
   */
  static final String ENDPOINT = null;

  /**
   * String value of "<code>TestNG-Selenium</code>".
   */
  static final String LOGGING_PREFIX = "TestNG-Selenium";

  /**
   * String value of "<code>Phone</code>".
   */
  static final String SCREENSIZE = "Phone";

  /**
   * Value of <code>System.getProperty("java.io.tmpdir")</code>.
   */
  static final String TMPDIR = System.getProperty("java.io.tmpdir");

  /**
   * No default user agent is provided.
   */
  static final String USER_AGENT = null;
}
