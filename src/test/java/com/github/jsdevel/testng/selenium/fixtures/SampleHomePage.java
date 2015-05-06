package com.github.jsdevel.testng.selenium.fixtures;

import com.github.jsdevel.testng.selenium.AbstractPage;

public class SampleHomePage extends AbstractPage<SampleHomePage> {

  @Override
  public SampleHomePage assertPageIsViewableFrom(String proposedUrl) {
    assertCurrentUrlPathStartsWith("/");
    return this;
  }
}
