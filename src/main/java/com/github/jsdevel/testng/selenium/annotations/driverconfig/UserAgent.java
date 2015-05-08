package com.github.jsdevel.testng.selenium.annotations.driverconfig;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Using this annotation causes the {@link org.openqa.selenium.WebDriver}'s
 * user-agent to be set to the value given.
 * 
 * @author Joseph Spencer
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UserAgent {
  String value();
}
