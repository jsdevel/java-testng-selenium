package com.github.jsdevel.testng.selenium.fixtures;

import com.github.jsdevel.testng.selenium.PageFactory;

public interface SamplePageFactory extends PageFactory {
  SampleHomePage getHomePage();  
  SampleHomePage getHomePage(String path);  
  // Used to throw an Exception in the test.
  String getFoo();
}
