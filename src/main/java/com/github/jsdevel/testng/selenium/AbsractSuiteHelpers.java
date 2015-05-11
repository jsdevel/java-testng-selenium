package com.github.jsdevel.testng.selenium;

import com.github.jsdevel.testng.selenium.annotations.driverconfig.UserAgent;
import com.github.jsdevel.testng.selenium.annotations.drivers.Chrome;
import com.github.jsdevel.testng.selenium.annotations.drivers.Firefox;
import com.github.jsdevel.testng.selenium.annotations.drivers.InternetExplorer;
import com.github.jsdevel.testng.selenium.annotations.screensizes.Desktop;
import com.github.jsdevel.testng.selenium.annotations.screensizes.LargeDesktop;
import com.github.jsdevel.testng.selenium.annotations.screensizes.Phone;
import com.github.jsdevel.testng.selenium.annotations.screensizes.Tablet;
import com.github.jsdevel.testng.selenium.config.Config;
import static com.github.jsdevel.testng.selenium.config.Config.TMPDIR;
import com.github.jsdevel.testng.selenium.exceptions.MissingPageFactoryException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.anthavio.phanbedder.Phanbedder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Internal helpers for AbstractSuite.
 * 
 * @author Joe Spencer
 */
class AbsractSuiteHelpers {
  private static final File phantomBinary = Phanbedder.unpack();
  static final File SCREENSHOT_DIR;

  static {
    SCREENSHOT_DIR = new File(TMPDIR, "screenshots");
    SCREENSHOT_DIR.mkdirs();
  }

  static <PF extends PageFactory> void addPageFactory(MethodContextImpl context) {
    Class<?> suite = context.method.getDeclaringClass();
    ParameterizedType abstractSuite;

    try {
       abstractSuite = (ParameterizedType)suite.getGenericSuperclass();
    } catch (ClassCastException e) {
      throw new MissingPageFactoryException(
          "AbstractSuite must receive Type parameters I.E. class MySuite " +
          "extends AbstractSuite<MyPageFactory>.  None were given in " +
          suite.getName());
    }

    Class<PF> pageFactoryClass = (Class<PF>) abstractSuite.getActualTypeArguments()[0];
    context.setPageFactory(PageFactoryProxy.newInstance(pageFactoryClass, context));
  } 

  static void addScreensize(MethodContextImpl context) {
    Method method = context.method;
    WebDriver driver = context.getWebDriver();

    Dimension testConfiguredDimension = getDimension(method, context);

    if (testConfiguredDimension == null) {
      try {
        testConfiguredDimension = getDimension(ScreenSizeHelper.class
            .getDeclaredMethod(Config.SCREENSIZE.toLowerCase()), context);
      } catch (NoSuchMethodException | SecurityException e) {
        // this should never get reached.
      }
    }

    if (testConfiguredDimension != null) {
      driver.manage().window().setSize(testConfiguredDimension);
    }
  }

  static void addWebDriver(MethodContextImpl context) {
    Method method = context.method;
    if (method.isAnnotationPresent(Chrome.class) ||
        Config.DRIVER.equalsIgnoreCase("chrome")) {
      addChromeDriver(context);
    } else if (method.isAnnotationPresent(Firefox.class) ||
               Config.DRIVER.equalsIgnoreCase("firefox")) {
      addFirefoxDriver(context);
    } else if (method.isAnnotationPresent(InternetExplorer.class) ||
               Config.DRIVER.equalsIgnoreCase("internetexplorer")) {
      addInternetExplorerDriver(context);
    } else {
      addPhantomJSDriver(context);
    }
  }

  static void addUserAgent(MethodContextImpl context) {
    Method method = context.method;
    if (method.isAnnotationPresent(UserAgent.class)) {
      context.setUserAgent(method.getAnnotation(UserAgent.class).value()); 
    }
  }

  static String getTestName(Method method) {
    return method.getDeclaringClass().getName() + ":" + method.getName();
  }

  static void takeScreenshot(MethodContextImpl context) throws IOException {
      File screenshotTarget = new File(SCREENSHOT_DIR, getTestName(
          context.method) + ".png");
      context.log("Saving a screenshot to " +
          screenshotTarget.getAbsolutePath());
      File screenshot = ((TakesScreenshot) context.getWebDriver())
          .getScreenshotAs(OutputType.FILE);
      FileUtils.copyFile(screenshot, screenshotTarget);
  }

  // Private methods.
  private static void addChromeDriver(MethodContextImpl context) {
    ChromeDriver driver = new ChromeDriver();
    context.setWebDriver(driver);
  }

  private static void addFirefoxDriver(MethodContextImpl context) {
    /*FirefoxDriver driver = new FirefoxDriver();
    context.setWebDriver(driver);*/
  }

  private static void addInternetExplorerDriver(MethodContextImpl context) {
    /*InternetExplorerDriver driver = new InternetExplorerDriver();
    context.setWebDriver(driver);*/
  }

  private static void addPhantomJSDriver(MethodContextImpl context) {
    DesiredCapabilities dcaps = new DesiredCapabilities();
    dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
        phantomBinary.getAbsolutePath());
    List<String> phantomCliArgs = new ArrayList();
    phantomCliArgs.add("--web-security=false");
    phantomCliArgs.add("--ignore-ssl-errors=true");
    phantomCliArgs.add("--ssl-protocol=any");

    List<String> ghostdriverCliArgs = new ArrayList();

    if (!Config.DEBUG) {
      phantomCliArgs.add("--webdriver-loglevel=ERROR");
      ghostdriverCliArgs.add("--logLevel=ERROR");
      Logger.getLogger(PhantomJSDriverService.class.getName()).setLevel(
          Level.OFF);
    }

    /*new String[] {                                                                  
          "--cookies-file=" + TestHelper.getPhantomCookieFilePath(context),             
          "--local-storage-path=/some/path" + TestHelper.getLocalStoragePath(context)
        });*/                                                                          

    dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS,
        phantomCliArgs.toArray(new String[]{}));

    dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS,
        ghostdriverCliArgs.toArray(new String[]{}));

    if (context.getUserAgent() != null) {
      dcaps.setCapability("phantomjs.page.settings.userAgent", context.getUserAgent()); 
    }

    PhantomJSDriver driver = new PhantomJSDriver(dcaps);
    context.setWebDriver(driver);
  }

  private static Dimension getDimension(Method method, MethodContextImpl context) {
    if (method.isAnnotationPresent(Phone.class)) {
      Phone dimension = method.getAnnotation(Phone.class);
      context.setScreensize(dimension);
      return new Dimension(dimension.width(), dimension.height());
    } else if (method.isAnnotationPresent(Tablet.class)) {
      Tablet dimension = method.getAnnotation(Tablet.class);
      context.setScreensize(dimension);
      return new Dimension(dimension.width(), dimension.height());
    } else if (method.isAnnotationPresent(Desktop.class)) {
      Desktop dimension = method.getAnnotation(Desktop.class);
      context.setScreensize(dimension);
      return new Dimension(dimension.width(), dimension.height());
    } else if (method.isAnnotationPresent(LargeDesktop.class)) {
      LargeDesktop dimension = method.getAnnotation(LargeDesktop.class);
      context.setScreensize(dimension);
      return new Dimension(dimension.width(), dimension.height());
    }

    return null;
  }

  private static class ScreenSizeHelper {
    @Phone
    static void phone(){}
    @Tablet
    static void tablet(){}
    @Desktop
    static void deskop(){}
    @LargeDesktop
    static void lagedeskop(){}
  }
}
