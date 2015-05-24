package com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory;

import com.github.jsdevel.testng.selenium.PageFactory;

public interface FixturePageFactory extends PageFactory {
  FixtureHomePage getFixtureHomePage();
  FixtureHomePage getFixtureHomePage(String desiredUrl);
}