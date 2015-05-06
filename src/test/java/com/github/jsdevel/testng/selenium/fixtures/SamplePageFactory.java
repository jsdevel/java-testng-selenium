package com.github.jsdevel.testng.selenium.fixtures;

import com.github.jsdevel.testng.selenium.AbstractPageFactory;

public class SamplePageFactory extends AbstractPageFactory {
  public SampleHomePage getHomePage() {
    return initializePage("/", new SampleHomePage());
  }
}