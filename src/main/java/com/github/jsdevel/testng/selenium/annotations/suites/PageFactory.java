package com.github.jsdevel.testng.selenium.annotations.suites;

import com.github.jsdevel.testng.selenium.AbstractPageFactory;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used in conjunction with suite classes, this instantiates the provided
 * AbstractPageFactory to each MethodContext.
 * 
 * @author Joe Spencer
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PageFactory {
  Class<? extends AbstractPageFactory> value(); 
}
