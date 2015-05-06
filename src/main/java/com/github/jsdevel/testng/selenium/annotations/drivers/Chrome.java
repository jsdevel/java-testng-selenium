package com.github.jsdevel.testng.selenium.annotations.drivers;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Using this annotation with @Test will cause the current MethodContext to
 * receive a WebDriver instance of Chrome.
 * 
 * @author Joseph Spencer
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Chrome {
  
}
