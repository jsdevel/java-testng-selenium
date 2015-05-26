package com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory;

import com.github.jsdevel.testng.selenium.Browser;

public interface FixturePageWithTypeParametersInMethods
    extends FixtureComponentWithTypeParametersInMethodName<FixturePageWithTypeParametersInMethods>,
            Browser {
  <T> FixturePageWithTypeParametersInMethods doSomethingOnFixturePageWithTypeParametersInMethods(); 
}
