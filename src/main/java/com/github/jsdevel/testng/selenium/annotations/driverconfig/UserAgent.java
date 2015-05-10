package com.github.jsdevel.testng.selenium.annotations.driverconfig;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation allows individual tests to configure the user agent in the
 * current {@link org.openqa.selenium.WebDriver}.
 * 
 * @author Joseph Spencer
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UserAgent {
  String value();
}
