package com.github.jsdevel.testng.selenium.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Constants representing "classpath:/testng-selenium.properties" configuration.
 * 
 * @see Config
 * 
 * @author Joe Spencer
 */
class PropertiesConfig {
  /**
   * Taken from testng.selenium.debug in the properties file.
   */
  static final String DEBUG;

  /**
   * Taken from testng.selenium.driver in the properties file.
   */
  static final String DRIVER;

  /**
   * Taken from testng.selenium.endpoint in the properties file.
   */
  static final String ENDPOINT;

  /**
   * Taken from testng.selenium.logging_prefix in the properties file.
   */
  static final String LOGGING_PREFIX;

  /**
   * Taken from testng.selenium.screensize in the properties file.
   */
  static final String SCREENSIZE;

  /**
   * Taken from testng.selenium.tmpdir in the properties file.
   */
  static final String TMPDIR;

  /**
   * Taken from testng.selenium.driverconfig.useragent in the properties file.
   */
  static final String USER_AGENT;

  static {
    String debug = ConfigDefaults.DEBUG;
    String driver = ConfigDefaults.DRIVER;
    String endpoint = ConfigDefaults.ENDPOINT;
    String loggingPrefix = ConfigDefaults.LOGGING_PREFIX;
    String screensize = ConfigDefaults.SCREENSIZE;
    String tmpDir = ConfigDefaults.TMPDIR;
    String userAgent = ConfigDefaults.USER_AGENT;

    try (InputStream propertiesInputStream = PropertiesConfig.class
        .getResourceAsStream("/testng-selenium.properties")) {
      if (propertiesInputStream != null) {
        try {
          Properties properties = new Properties();
          properties.load(propertiesInputStream);
          debug = properties.getProperty(ConfigOptions.DEBUG, debug); 
          driver = properties.getProperty(ConfigOptions.DRIVER, driver); 
          endpoint = properties.getProperty(ConfigOptions.ENDPOINT, endpoint); 
          loggingPrefix = properties.getProperty(
              ConfigOptions.LOGGING_PREFIX, loggingPrefix); 
          screensize = properties.getProperty(
              ConfigOptions.SCREENSIZE, screensize); 
          tmpDir = properties.getProperty(ConfigOptions.TMPDIR, tmpDir); 
          userAgent = properties.getProperty(
              ConfigOptions.USER_AGENT, userAgent); 
        } catch (IOException e) {
          Logger.getLogger(PropertiesConfig.class.getName())
              .log(Level.SEVERE, "Failed to load testng-selenium.properties.", e);
          System.exit(1);
        }
      }
    } catch (IOException e) {
      Logger.getLogger(PropertiesConfig.class.getName())
          .log(Level.SEVERE, "Failed to close testng-selenium.properties.", e);
      System.exit(1);
    }

    DEBUG = debug;
    DRIVER = driver;
    ENDPOINT = endpoint;
    LOGGING_PREFIX = loggingPrefix;
    SCREENSIZE = screensize;
    TMPDIR = tmpDir;
    USER_AGENT = userAgent;
  }
}
