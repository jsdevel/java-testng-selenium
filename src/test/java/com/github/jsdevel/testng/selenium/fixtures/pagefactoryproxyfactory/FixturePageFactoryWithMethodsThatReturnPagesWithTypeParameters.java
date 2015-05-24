package com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory;

import com.github.jsdevel.testng.selenium.PageFactory;

public interface FixturePageFactoryWithMethodsThatReturnPagesWithTypeParameters extends PageFactory {
  FixturePageWithTypeParameters<FixturePageWithTypeParameters> getFixturePageWithTypeParameters();
  FixturePageWithTypeParameters<FixturePageWithTypeParameters> getFixturePageWithTypeParameters(String foo);
}
