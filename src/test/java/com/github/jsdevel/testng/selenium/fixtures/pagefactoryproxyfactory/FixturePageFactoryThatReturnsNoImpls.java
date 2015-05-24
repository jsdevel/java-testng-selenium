package com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory;

import com.github.jsdevel.testng.selenium.PageFactory;

public interface FixturePageFactoryThatReturnsNoImpls extends PageFactory {
  FixtureFooPage getFixtureFooPage();  
  FixtureFooPage getFixtureFooPage(String foo);  
  FixtureBooPage getFixtureBooPage();  
  FixtureBooPage getFixtureBooPage(String foo);  
}
