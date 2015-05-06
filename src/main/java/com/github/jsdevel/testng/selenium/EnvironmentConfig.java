package com.github.jsdevel.testng.selenium;

/**
 * This class represents environment configuration understood by
 * testng-selenium.
 * 
 * @author Joe Spencer
 */
class EnvironmentConfig {
  public static final String ENDPOINT = System.getProperty(
      SystemProperties.ENDPOINT);
  public static final String LOGGING_PREFIX = System.getProperty(
      SystemProperties.LOGGING_PREFIX, EnvironmentDefaults.LOGGING_PREFIX);
  public static final String SCREENSIZE = System.getProperty(
      SystemProperties.SCREENSIZE, EnvironmentDefaults.SCREENSIZE);

  static {
    if (ENDPOINT == null) {
      System.out.println(SystemProperties.ENDPOINT +
          " must be a configured System property!"); 
      System.exit(1);
    } else {
      System.out.println(SystemProperties.ENDPOINT + " set to " + ENDPOINT);
    }

    System.out.println(SystemProperties.LOGGING_PREFIX + " was set to " + LOGGING_PREFIX);
  }
}
