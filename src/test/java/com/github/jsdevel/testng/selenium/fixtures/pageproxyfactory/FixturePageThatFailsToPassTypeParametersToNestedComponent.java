package com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory;

import com.github.jsdevel.testng.selenium.Browser;

public interface FixturePageThatFailsToPassTypeParametersToNestedComponent extends FixtureComponentThatFailsToPassTypeParametersToANestedComponent<FixturePageThatFailsToPassTypeParametersToNestedComponent>, Browser {
  
}
