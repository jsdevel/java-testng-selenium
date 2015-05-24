package com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory;

import com.github.jsdevel.testng.selenium.PageFactory;

public interface FixturePageFactoryWithUnhandledParameterTypes extends PageFactory {
  FixtureHomePage getFixtureHomePage();  
  FixtureHomePage getFixtureHomePage(Object obj);  
}
