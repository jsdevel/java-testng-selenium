package com.github.jsdevel.testng.selenium;

import com.github.jsdevel.testng.selenium.config.Config;
import java.util.List;

/**
 * A logger that prefixes output.
 * 
 * @see com.github.jsdevel.testng.selenium.config.Config
 * 
 * @author Joe Spencer
 */
public class TestNGSeleniumLogger {
  /**
   * Logs output with a logging prefix (debug mode enabled only).
   * 
   * @see com.github.jsdevel.testng.selenium.config.Config
   * 
   * @param msg 
   */
  public static void debug(String msg) {
    if (Config.DEBUG) {
      log(msg);
    }
  }

  /**
   * Logs output with a logging prefix.
   * 
   * @see com.github.jsdevel.testng.selenium.config.Config
   * 
   * @param msg The message to log.
   */
  public static void log(String msg) {
    System.out.println(prefixed(msg));
  } 

  /**
   * Logs a group of messages in a single buffered operation.  Using this method
   * will not interleave output with other tests when running in parallel.
   * 
   * @param messages 
   */
  public static void log(List<String> messages) {
    if (messages != null) {
      StringBuilder builder = new StringBuilder();
      for (String msg: messages) {
        builder.append(prefixed(msg)).append('\n');
      }
      System.out.print(builder.toString());
    }
  }

  private static String prefixed(String msg) {
    return "[" + Config.LOGGING_PREFIX + "] " + msg;
  }
}
