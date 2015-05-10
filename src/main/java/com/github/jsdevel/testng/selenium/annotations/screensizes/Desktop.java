package com.github.jsdevel.testng.selenium.annotations.screensizes;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Using this annotation with {@link org.testng.annotations.Test} will cause the
 * current {@link com.github.jsdevel.testng.selenium.MethodContext} to receive a
 * {@link org.openqa.selenium.WebDriver} instance set to Desktop
 * dimension 992x600 (based off of <a href="http://getbootstrap.com/css/#grid-media-queries">bootstrap breakpoints</a>).
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
