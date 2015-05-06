package com.github.jsdevel.testng.selenium;

import com.github.jsdevel.testng.selenium.annotations.drivers.Chrome;
import com.github.jsdevel.testng.selenium.annotations.drivers.Firefox;
import com.github.jsdevel.testng.selenium.annotations.drivers.InternetExplorer;
import com.github.jsdevel.testng.selenium.annotations.suites.PageFactory;
import com.github.jsdevel.testng.selenium.exceptions.MissingPageFactoryException;
import com.github.jsdevel.testng.selenium.exceptions.PageFactoryInstantiationException;
import java.io.File;
import java.lang.reflect.Method;
import net.anthavio.phanbedder.Phanbedder;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
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

  static <PF extends AbstractPageFactory> void addPageFactory(MethodContext context) {
    Class<?> suite = context.method.getDeclaringClass();
    PageFactory pageFactoryAnnotation = suite.getAnnotation(PageFactory.class);

    if (pageFactoryAnnotation == null) {
      throw new MissingPageFactoryException(
          "testng-selenium suites must be annotated with a page factory.  " +
          "None found for " + suite.getCanonicalName());
    }

    try {
      PF pageFactory = (PF) pageFactoryAnnotation.value().newInstance();
      pageFactory.setMethodContext(context);
      pageFactory.setEndoint(EnvironmentConfig.ENDPOINT);
      pageFactory.setWebDriver(context.getWebDriver());
      context.setPageFactory(pageFactory);
    } catch (InstantiationException | IllegalAccessException e) {
      throw new PageFactoryInstantiationException(e);
    }
  } 

  static synchronized void addWebDriver(MethodContext context) {
    Method method = context.method;
    if (method.isAnnotationPresent(Chrome.class)) {
      addChromeDriver(context);
    } else if (method.isAnnotationPresent(Firefox.class)) {
      addFirefoxDriver(context);
    } else if (method.isAnnotationPresent(InternetExplorer.class)) {
      addInternetExplorerDriver(context);
    } else {
      addPhantomJSDriver(context);
    }
  }

  private static void addChromeDriver(MethodContext context) {
    ChromeDriver driver = new ChromeDriver();
    context.setWebDriver(driver);
  }

  private static void addFirefoxDriver(MethodContext context) {
    FirefoxDriver driver = new FirefoxDriver();
    context.setWebDriver(driver);
  }

  private static void addInternetExplorerDriver(MethodContext context) {
    InternetExplorerDriver driver = new InternetExplorerDriver();
    context.setWebDriver(driver);
  }

  private static void addPhantomJSDriver(MethodContext context) {
    DesiredCapabilities dcaps = new DesiredCapabilities();
    dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
        phantomBinary.getAbsolutePath());
    /*
    dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS,                      
        new String[] {                                                                  
          "--cookies-file=" + TestHelper.getPhantomCookieFilePath(context),             
          "--web-security=false",                                                       
          "--ignore-ssl-errors=true",                                                   
          "--ssl-protocol=any",                                                         
          "--local-storage-path=/some/path" + TestHelper.getLocalStoragePath(context)
        });                                                                             
    dcaps.setCapability("phantomjs.page.settings.userAgent", context.userAgent); 
            */
    PhantomJSDriver driver = new PhantomJSDriver(dcaps);
    //driver.manage().window().setSize(context.getDimension());
    context.setWebDriver(driver);
  }
}
