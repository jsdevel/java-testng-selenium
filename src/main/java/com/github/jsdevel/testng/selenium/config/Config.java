package com.github.jsdevel.testng.selenium.config;

import static com.github.jsdevel.testng.selenium.TestNGSeleniumLogger.debug;
import static com.github.jsdevel.testng.selenium.TestNGSeleniumLogger.log;
import java.io.File;

/**
 * This class represents environment configuration understood by
 * testng-selenium.  Configuration may be taken from a properties file on the
 * classpath (testng-selenium.properties), or from system properties.
 * 
 * @see ConfigOptions
 * 
 * @author Joe Spencer
 */
public class Config {
  /**
   * Enables debug mode.  Debug mode will output verbose logging.  Recognized
   * values are "enabled", "disabled".  The default value is "disabled".
   * 
   * @see ConfigOptions#DEBUG
   */
  public static final boolean DEBUG = System.getProperty(ConfigOptions.DEBUG, PropertiesConfig.DEBUG).equalsIgnoreCase("enabled");

  /**
   * The WebDriver used for the test run.  Possible values are:
   * Chrome, Firefox, InternetExplorer, and PhantomJS (the default).
   * 
   * This value may be overridden on a test by test basis with annotations.
   * 
   * @see ConfigOptions#DRIVER
   * @see com.github.jsdevel.testng.selenium.annotations.drivers.Chrome
   * @see com.github.jsdevel.testng.selenium.annotations.drivers.Firefox
   * @see com.github.jsdevel.testng.selenium.annotations.drivers.InternetExplorer
   * @see com.github.jsdevel.testng.selenium.annotations.drivers.PhantomJS
   */
  public static final String DRIVER = System.getProperty(ConfigOptions.DRIVER, PropertiesConfig.DRIVER);

  /**
   * The endpoint used for the test run.  By default this is a null value, and
   * will cause a failed System.exit() call to occur if null.  This value is
   * used to resolve desired URLs when requesting page objects from page
   * factories.
   * 
   * @see ConfigOptions#ENDPOINT
   * @see com.github.jsdevel.testng.selenium.AbstractSuite
   */
  public static final String ENDPOINT = System.getProperty(ConfigOptions.ENDPOINT, PropertiesConfig.ENDPOINT);

  /**
   * The prefix used when logging.  By default this is set to "TestNG-Selenium".
   * 
   * @see ConfigOptions#LOGGING_PREFIX
   * @see com.github.jsdevel.testng.selenium.TestNGSeleniumLogger
   */
  public static final String LOGGING_PREFIX = System.getProperty(ConfigOptions.LOGGING_PREFIX, PropertiesConfig.LOGGING_PREFIX);

  /**
   * The default screen size used for test runs.  Possible values are:
   * LargeDesktop, Desktop, Tablet, and Phone.  The default screen size is
   * Phone.
   * 
   * This value may be overridden on a test by test basis with annotations.
   * 
   * @see ConfigOptions#SCREENSIZE
   * @see com.github.jsdevel.testng.selenium.annotations.screensizes.LargeDesktop
   * @see com.github.jsdevel.testng.selenium.annotations.screensizes.Desktop
   * @see com.github.jsdevel.testng.selenium.annotations.screensizes.Tablet
   * @see com.github.jsdevel.testng.selenium.annotations.screensizes.Phone
   */
  public static final String SCREENSIZE = System.getProperty(ConfigOptions.SCREENSIZE, PropertiesConfig.SCREENSIZE);

  /**
   * The temporary directory used to store screen shots, cookie files, and other
   * related data needed for test runs.  The default value is set to the
   * platform specific default temp directory location.
   * 
   * @see ConfigOptions#TMPDIR
   */
  public static final String TMPDIR = System.getProperty(ConfigOptions.TMPDIR, PropertiesConfig.TMPDIR);
 
  /**
   * The default User-Agent string to use in the WebDriver.  This overrides the
   * WebDriver's default User-Agent.
   * 
   * This value may be overridden on a test by test basis with annotations.
   * 
   * @see ConfigOptions#USER_AGENT
   * @see com.github.jsdevel.testng.selenium.annotations.driverconfig.UserAgent
   */
  public static final String USER_AGENT = System.getProperty(ConfigOptions.USER_AGENT, PropertiesConfig.USER_AGENT);

  private static final String DRIVER_OPTIONS = "Chrome,Firefox,InternetExplorer,PhantomJS";
  private static final String SCREENSIZE_OPTIONS = "LargeDesktop,Desktop,Tablet,Phone";

  static {
    if (!("," + DRIVER_OPTIONS + ",").toLowerCase()
            .contains(DRIVER.toLowerCase())) {
      log(ConfigOptions.DRIVER + " must be one of " +
          DRIVER_OPTIONS);
      System.exit(1); 
    }

    if (ENDPOINT == null) {
      log(ConfigOptions.ENDPOINT +
          " must be a configured System property!"); 
      System.exit(1);
    }

    if (!("," + SCREENSIZE_OPTIONS + ",").contains("," + SCREENSIZE + ",")) {
      log(ConfigOptions.SCREENSIZE + " must be one of " +
          SCREENSIZE_OPTIONS);
      log("Saw " + SCREENSIZE);
      System.exit(1);
    }

    if (TMPDIR == null) {
      log(ConfigOptions.TMPDIR +
          " must be a configured System property!"); 
      System.exit(1);
    } else {
      File tmpdir = new File(TMPDIR);
      if (tmpdir.exists()) {
        if (!tmpdir.isDirectory()) {
          log(ConfigOptions.TMPDIR +
              " cannot use non directories for tmp dir."); 
          System.exit(1);
        }
      } else {
        tmpdir.mkdirs();
      }
    }

    debug(ConfigOptions.DEBUG + " set to " + DEBUG);
    debug(ConfigOptions.DRIVER + " set to " + DRIVER);
    debug(ConfigOptions.ENDPOINT + " set to " + ENDPOINT);
    debug(ConfigOptions.LOGGING_PREFIX + " set to " + LOGGING_PREFIX);
    debug(ConfigOptions.SCREENSIZE + " set to " + SCREENSIZE);
    debug(ConfigOptions.TMPDIR + " set to " + TMPDIR);
    debug(ConfigOptions.USER_AGENT + " set to " + USER_AGENT);
  }
}
