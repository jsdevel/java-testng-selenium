package com.github.jsdevel.testng.selenium.annotations.screensizes;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Using this annotation with @Test will cause the current MethodContext to
 * receive a WebDriver instance set to Desktop dimension 992x600 (based off of
 * bootstrap breakpoints).
 * 
 * @author Joseph Spencer
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Desktop {
  int height() default 600;
  int width() default 992;
}
