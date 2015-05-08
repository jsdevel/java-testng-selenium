package com.github.jsdevel.testng.selenium.samples;

import com.github.jsdevel.testng.selenium.PageFactory;

public interface SamplePageFactory extends PageFactory {
  GoogleHomePage getHomePage();  
  GoogleHomePage getHomePage(String path);  
  GoogleSearchResultsPage getSearchResultsPage();
  GoogleSearchResultsPage getSearchResultsPage(String path);

  // Used to throw an Exception in the test.
  String getFoo();
}
