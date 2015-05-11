package com.github.jsdevel.testng.selenium.environment;

import java.io.File;

/**
 * This class represents environment configuration understood by
 * testng-selenium.
 * 
 * @author Joe Spencer
 */
public class EnvironmentConfig {
  /**
   * The WebDriver used for the test run.  Possible values are:
   * Chrome, Firefox, InternetExplorer, and PhantomJS (the default).  This value
   * may be overridden by using annotations.
   * 
   * @see com.github.jsdevel.testng.selenium.annotations.drivers.Chrome
   * @see com.github.jsdevel.testng.selenium.annotations.drivers.Firefox
   * @see com.github.jsdevel.testng.selenium.annotations.drivers.InternetExplorer
   * @see com.github.jsdevel.testng.selenium.annotations.drivers.PhantomJS
   */
  public static final String DRIVER = System.getProperty(
      SystemProperties.DRIVER, EnvironmentConfigDefaults.DRIVER);

  /**
   * The endpoint used for the test run.  By default this is a null value, and
   * will cause a failed System.exit() call to occur if null.  This value is
   * used to resolve desired URLs when requesting page objects from
   * {@link PageFactory}.
   * 
   * @see com.github.jsdevel.testng.selenium.AbstractSuite
   */
  public static final String ENDPOINT = System.getProperty(
      SystemProperties.ENDPOINT, EnvironmentConfigDefaults.ENDPOINT);

  /**
   * The prefix used when logging from {@link MethodContext}.  By default this
   * is set to "TestNG-Selenium".
   */
  public static final String LOGGING_PREFIX = System.getProperty(
      SystemProperties.LOGGING_PREFIX, EnvironmentConfigDefaults.LOGGING_PREFIX);

  /**
   * The default screen size used for test runs.  Possible values are:
   * LargeDesktop, Desktop, Tablet, and Phone.  The default screen size is
   * Phone.
   * 
   * @see com.github.jsdevel.testng.selenium.annotations.screensizes.LargeDesktop
   * @see com.github.jsdevel.testng.selenium.annotations.screensizes.Desktop
   * @see com.github.jsdevel.testng.selenium.annotations.screensizes.Tablet
   * @see com.github.jsdevel.testng.selenium.annotations.screensizes.Phone
   */
  public static final String SCREENSIZE = System.getProperty(
      SystemProperties.SCREENSIZE, EnvironmentConfigDefaults.SCREENSIZE);

  /**
   * The temporary directory used to store screen shots, cookie files, and other
   * related data needed for test runs.  The default value is set to the
   * platform specific default temp directory location.
   */
  public static final String TMPDIR = System.getProperty(
      SystemProperties.TMPDIR, EnvironmentConfigDefaults.TMPDIR);

  private static final String DRIVER_OPTIONS = "Chrome,Firefox,InternetExplorer,PhantomJS";
  private static final String SCREENSIZE_OPTIONS = "LargeDesktop,Desktop,Tablet,Phone";

  static {
    if (!("," + DRIVER_OPTIONS + ",").toLowerCase()
            .contains(DRIVER.toLowerCase())) {
      System.out.println(SystemProperties.DRIVER + " must be one of " +
          DRIVER_OPTIONS);
      System.exit(1); 
    }

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
      System.out.println(SystemProperties.TMPDIR + " set to " + TMPDIR);
    }
  }
}
