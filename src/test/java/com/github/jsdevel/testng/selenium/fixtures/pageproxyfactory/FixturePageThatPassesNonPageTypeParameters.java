package com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory;

import com.github.jsdevel.testng.selenium.Browser;

public interface FixturePageThatPassesNonPageTypeParameters extends FixtureComponentThatExpectsTypeParameters<String>, Browser {
  FixturePageThatPassesNonPageTypeParameters doSomething();
}
