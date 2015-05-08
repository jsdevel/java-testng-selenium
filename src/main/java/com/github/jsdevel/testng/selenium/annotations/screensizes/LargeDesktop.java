package com.github.jsdevel.testng.selenium.annotations.screensizes;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Using this annotation with {@link org.testng.annotations.Test} will cause the
 * current {@link com.github.jsdevel.testng.selenium.MethodContext} to receive a
 * {@link org.openqa.selenium.WebDriver} instance set to a Large Desktop
 * dimension 1200x800 (based off of bootstrap breakpoints).
 * 
 * @author Joseph Spencer
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LargeDesktop {
  int height() default 800;
  int width() default 1200;
}
