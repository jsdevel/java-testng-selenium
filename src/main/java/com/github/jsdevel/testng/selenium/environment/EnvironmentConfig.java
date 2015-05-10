package com.github.jsdevel.testng.selenium.environment;

import java.io.File;

/**
 * This class represents environment configuration understood by
 * testng-selenium.
 * 
 * @author Joe Spencer
 */
public class EnvironmentConfig {
  public static final String ENDPOINT = System.getProperty(
      SystemProperties.ENDPOINT, EnvironmentConfigDefaults.ENDPOINT);

  public static final String LOGGING_PREFIX = System.getProperty(
      SystemProperties.LOGGING_PREFIX, EnvironmentConfigDefaults.LOGGING_PREFIX);

  public static final String SCREENSIZE = System.getProperty(
      SystemProperties.SCREENSIZE, EnvironmentConfigDefaults.SCREENSIZE);

  public static final String TMPDIR = System.getProperty(
      SystemProperties.TMPDIR, EnvironmentConfigDefaults.TMPDIR);

  private static final String SCREENSIZE_OPTIONS = "LargeDesktop,Desktop,Tablet,Phone";

  static {
    if (ENDPOINT == null) {
      System.out.println(SystemProperties.ENDPOINT +
          " must be a configured System property!"); 
      System.exit(1);
    } else {
      System.out.println(SystemProperties.ENDPOINT + " set to " + ENDPOINT);
    }

    System.out.println(SystemProperties.LOGGING_PREFIX + " was set to " + LOGGING_PREFIX);

    if (!("," + SCREENSIZE_OPTIONS + ",").contains("," + SCREENSIZE + ",")) {
      System.out.println(SystemProperties.SCREENSIZE + " must be one of " +
          SCREENSIZE_OPTIONS);
      System.out.println("Saw " + SCREENSIZE);
      System.exit(1);
    }

    if (TMPDIR == null) {
      System.out.println(SystemProperties.TMPDIR +
          " must be a configured System property!"); 
      System.exit(1);
    } else {
      File tmpdir = new File(TMPDIR);
      if (tmpdir.exists()) {
        if (!tmpdir.isDirectory()) {
          System.out.println(SystemProperties.TMPDIR +
              " cannot use non directories for tmp dir."); 
          System.exit(1);
        }
      } else {
        tmpdir.mkdirs();
      }
      System.out.println(SystemProperties.ENDPOINT + " set to " + ENDPOINT);
    }
  }
}
