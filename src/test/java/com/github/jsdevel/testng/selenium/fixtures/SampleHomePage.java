package com.github.jsdevel.testng.selenium.fixtures;

import com.github.jsdevel.testng.selenium.Page;
import java.net.URL;

public class SampleHomePage extends Page<SampleHomePage> {

  @Override
  protected boolean assertPageIsViewableFrom(URL proposedUrl) {
    return proposedUrl.getPath().startsWith("/");
  }
}
