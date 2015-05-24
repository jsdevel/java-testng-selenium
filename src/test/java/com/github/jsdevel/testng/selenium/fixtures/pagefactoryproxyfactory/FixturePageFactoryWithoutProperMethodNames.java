package com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory;

import com.github.jsdevel.testng.selenium.PageFactory;

public interface FixturePageFactoryWithoutProperMethodNames extends PageFactory {
  FixtureHomePage getFoo();  
  FixtureHomePage getBoo();  
}
