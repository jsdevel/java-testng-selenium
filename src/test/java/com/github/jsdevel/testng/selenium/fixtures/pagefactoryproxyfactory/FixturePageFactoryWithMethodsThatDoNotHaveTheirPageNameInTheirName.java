package com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory;

import com.github.jsdevel.testng.selenium.PageFactory;

public interface FixturePageFactoryWithMethodsThatDoNotHaveTheirPageNameInTheirName extends PageFactory {
  FixtureHomePage getFixtureFoo();  
}
