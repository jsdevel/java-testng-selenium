package com.github.jsdevel.testng.selenium;

import com.github.jsdevel.testng.selenium.annotations.suites.PageFactory;
import com.github.jsdevel.testng.selenium.fixtures.SamplePageFactory;
import org.testng.annotations.Test;

@PageFactory(SamplePageFactory.class)
public class SampleSuiteITest extends AbstractSuite<SamplePageFactory> {
  @Test
  public void we_should_be_able_to_view_google() {
    getPageFactory().getHomePage();
  }
}
