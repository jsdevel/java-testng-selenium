package com.github.jsdevel.testng.selenium.config;

import static com.github.jsdevel.testng.selenium.TestNGSeleniumLogger.debug;
import static com.github.jsdevel.testng.selenium.TestNGSeleniumLogger.log;
import java.io.File;

/**
 * This class represents environment configuration understood by
 * testng-selenium.
 * 
 * @author Joe Spencer
 */
public class Config {
  /**
   * Enables debug mode.  Debug mode will output verbose logging.  Recognized
   * values are "enabled", "disabled".  The default value is "disabled".
   * 
   * @see ConfigDefaults
   */
  public static final boolean DEBUG = System.getProperty(
      SystemProperties.DEBUG, ConfigDefaults.DEBUG).equalsIgnoreCase("enabled");

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
      SystemProperties.DRIVER, ConfigDefaults.DRIVER);

  /**
   * The endpoint used for the test run.  By default this is a null value, and
   * will cause a failed System.exit() call to occur if null.  This value is
   * used to resolve desired URLs when requesting page objects from page
   * factories.
   * 
   * @see com.github.jsdevel.testng.selenium.AbstractSuite
   */
  public static final String ENDPOINT = System.getProperty(
      SystemProperties.ENDPOINT, ConfigDefaults.ENDPOINT);

  /**
   * The prefix used when logging.  By default this is set to "TestNG-Selenium".
   */
  public static final String LOGGING_PREFIX = System.getProperty(
      SystemProperties.LOGGING_PREFIX, ConfigDefaults.LOGGING_PREFIX);

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
      SystemProperties.SCREENSIZE, ConfigDefaults.SCREENSIZE);

  /**
   * The temporary directory used to store screen shots, cookie files, and other
   * related data needed for test runs.  The default value is set to the
   * platform specific default temp directory location.
   */
  public static final String TMPDIR = System.getProperty(
      SystemProperties.TMPDIR, ConfigDefaults.TMPDIR);
 

  /**
   * The default User-Agent string to use in the WebDriver.  This overrides the
   * WebDriver's default User-Agent.  This value may be overridden on a test by
   * test basis via annotations.
   * 
   * @see com.github.jsdevel.testng.selenium.annotations.driverconfig.UserAgent
   */
  public static final String USER_AGENT = System.getProperty(
      SystemProperties.USER_AGENT, ConfigDefaults.USER_AGENT);

  private static final String DRIVER_OPTIONS = "Chrome,Firefox,InternetExplorer,PhantomJS";
  private static final String SCREENSIZE_OPTIONS = "LargeDesktop,Desktop,Tablet,Phone";

  static {
    if (!("," + DRIVER_OPTIONS + ",").toLowerCase()
            .contains(DRIVER.toLowerCase())) {
      log(SystemProperties.DRIVER + " must be one of " +
          DRIVER_OPTIONS);
      System.exit(1); 
    }

    if (ENDPOINT == null) {
      log(SystemProperties.ENDPOINT +
          " must be a configured System property!"); 
      System.exit(1);
    }

    if (!("," + SCREENSIZE_OPTIONS + ",").contains("," + SCREENSIZE + ",")) {
      log(SystemProperties.SCREENSIZE + " must be one of " +
          SCREENSIZE_OPTIONS);
      log("Saw " + SCREENSIZE);
      System.exit(1);
    }

    if (TMPDIR == null) {
      log(SystemProperties.TMPDIR +
          " must be a configured System property!"); 
      System.exit(1);
    } else {
      File tmpdir = new File(TMPDIR);
      if (tmpdir.exists()) {
        if (!tmpdir.isDirectory()) {
          log(SystemProperties.TMPDIR +
              " cannot use non directories for tmp dir."); 
          System.exit(1);
        }
      } else {
        tmpdir.mkdirs();
      }
    }

    debug(SystemProperties.DEBUG + " set to " + DEBUG);
    debug(SystemProperties.DRIVER + " set to " + DRIVER);
    debug(SystemProperties.ENDPOINT + " set to " + ENDPOINT);
    debug(SystemProperties.SCREENSIZE + " set to " + SCREENSIZE);
    debug(SystemProperties.TMPDIR + " set to " + TMPDIR);
    debug(SystemProperties.USER_AGENT + " set to " + USER_AGENT);
  }
}
