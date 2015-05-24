package com.github.jsdevel.testng.selenium;

import com.github.jsdevel.testng.selenium.exceptions.PageInstantiationException;
import com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixturePageFactory;
import com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixturePageNotHandledByPageFactory;
import com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixturePageThatFailsToPassTypeParametersToComponent;
import com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixturePageThatFailsToPassTypeParametersToNestedComponent;
import com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixturePageThatHasNestedComponents;
import com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixturePageThatPassesNonPageTypeParameters;
import com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixturePageWhosImplHasNoMatchingMethod;
import com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixturePageWithAComponentWhosImplHasNoMatchingMethod;
import com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixtureSimplePage;
import java.util.HashSet;
import java.util.Set;
import static org.testng.AssertJUnit.assertNotNull;
import org.testng.annotations.Test;

public class PageProxyFactoryITest {
  
  @Test
  public void we_should_be_able_to_register_a_simple_page() {
    registerPage(FixtureSimplePage.class);
  }

  @Test
  public void we_should_be_able_to_get_a_simple_page_after_it_has_been_registered() {
    registerPage(FixtureSimplePage.class);
    assertNotNull(PageProxyFactory.getPageProxy(FixtureSimplePage.class));
  }

  @Test
  public void we_should_be_able_to_register_a_page_with_nested_components() {
    registerPage(FixturePageThatHasNestedComponents.class);
  }

  @Test(expectedExceptions = PageInstantiationException.class, expectedExceptionsMessageRegExp = "Expected to see a method with signature \\[void doSomething\\(\\)\\] in com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixturePageWhosImplHasNoMatchingMethodImpl")
  public void we_should_throw_an_exception_if_the_page_interface_method_has_no_matching_method_in_the_impl() {
    registerPage(FixturePageWhosImplHasNoMatchingMethod.class);
  }

  @Test(expectedExceptions = PageInstantiationException.class, expectedExceptionsMessageRegExp = "Expected to see a method with signature \\[void doSomething\\(\\)\\] in com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixtureComponentWithAMethodNotMatchedInImplImpl")
  public void we_should_throw_an_exception_if_the_page_interface_has_a_component_without_a_matching_method_in_its_impl() {
    registerPage(FixturePageWithAComponentWhosImplHasNoMatchingMethod.class);
  }

  @Test(expectedExceptions = PageInstantiationException.class, expectedExceptionsMessageRegExp = "Expected Type parameters were not given to com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixtureComponentThatExpectsTypeParameters from com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixturePageThatFailsToPassTypeParametersToComponent")
  public void we_should_throw_an_exception_if_the_page_interface_does_not_pass_type_parameters_to_a_component_that_expects_them() {
    registerPage(FixturePageThatFailsToPassTypeParametersToComponent.class);
  }

  @Test(expectedExceptions = PageInstantiationException.class, expectedExceptionsMessageRegExp = "Expected Type parameters were not given to com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixtureComponentThatExpectsTypeParameters from com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixtureComponentThatFailsToPassTypeParametersToANestedComponent")
  public void we_should_throw_an_exception_if_the_page_interface_does_not_pass_type_parameters_to_a_nested_component_that_expects_them() {
    registerPage(FixturePageThatFailsToPassTypeParametersToNestedComponent.class);
  }

  @Test(expectedExceptions = PageInstantiationException.class, expectedExceptionsMessageRegExp = "Pages are the only allowed Type parameters.  The following Type parameters were given in com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixturePageThatPassesNonPageTypeParameters: java.lang.String")
  public void we_should_throw_an_exception_if_any_non_page_arguments_were_passed_as_type_parameters_to_components() {
    registerPage(FixturePageThatPassesNonPageTypeParameters.class);
  }

  @Test(expectedExceptions = PageInstantiationException.class, expectedExceptionsMessageRegExp = "The following pages were not handled by com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixturePageFactory: com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixturePageNotHandledByPageFactory")
  public void we_should_throw_an_exception_if_a_page_intoruces_a_page_not_known_by_the_PageFactory() {
    registerPage(FixturePageNotHandledByPageFactory.class);
  }








  // Helpers
  private void registerPage(Class<? extends Browser> clazz) {
    PageProxyFactory.registerPage(clazz, FixturePageFactory.class);
  }
}