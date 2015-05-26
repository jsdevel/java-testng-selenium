package com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory;

import com.github.jsdevel.testng.selenium.PageFactory;

public interface FixturePageFactoryWithAMethodThatAcceptsTypeParameters extends PageFactory {
  <T> FixtureHomePage  getFixtureHomePage();
  <T> FixtureHomePage  getFixtureHomePage(String foo);
}
