package com.github.jsdevel.testng.selenium;

import com.github.jsdevel.testng.selenium.exceptions.PageInstantiationException;
import org.testng.annotations.Test;
import com.github.jsdevel.testng.selenium.fixtures.SamplePageFactory;

public class SampleSuiteITest extends AbstractSuite<SamplePageFactory> {
  @Test
  public void we_should_be_able_to_view_google_with_phantom() {
    getPageFactory().getHomePage();
  }

  @Test
  public void we_should_be_able_to_pass_desired_urls_to_pages() {
    getPageFactory()
      .getHomePage("/intl/en/policies/privacy/?fg=1")
      .assertCurrentUrlPathStartsWith("/intl")
    ;
  }

  @Test(expectedExceptions = PageInstantiationException.class)
  public void we_should_get_an_error_when_calling_factory_methods_with_a_return_type_that_does_not_extend_Page() {
    getPageFactory().getFoo();
  }
}
