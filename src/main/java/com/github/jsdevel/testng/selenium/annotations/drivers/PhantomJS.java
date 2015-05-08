package com.github.jsdevel.testng.selenium.annotations.drivers;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Using this annotation with {@link org.testng.annotations.Test} will cause the
 * current {@link com.github.jsdevel.testng.selenium.MethodContext} to receive a
 * {@link org.openqa.selenium.WebDriver} instance of
 * {@link org.openqa.selenium.phantomjs.PhantomJSDriver}.
 * 
 * @author Joseph Spencer
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PhantomJS {
  
}
