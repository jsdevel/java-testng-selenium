package com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory;

import com.github.jsdevel.testng.selenium.PageFactory;

public interface FixturePageFactoryThatReturnsClasses extends PageFactory {
  FixtureBooPageClass getFixtureBooPageClass();  
}
