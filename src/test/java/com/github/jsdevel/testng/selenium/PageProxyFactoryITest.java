package com.github.jsdevel.testng.selenium;

import com.github.jsdevel.testng.selenium.exceptions.PageInstantiationException;
import com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixturePageFactory;
import com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixturePageNotHandledByPageFactory;
import com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixturePageThatFailsToPassTypeParametersToComponent;
import com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixturePageThatFailsToPassTypeParametersToNestedComponent;
import com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixturePageThatHasNestedComponents;
import com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixturePageThatIncludesAComponentWithNoImpl;
import com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixturePageThatPassesNonPageTypeParameters;
import com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixturePageWhosImplHasNoMatchingMethod;
import com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixturePageWithAComponentWhosImplHasNoMatchingMethod;
import com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixturePageWithMethodNotIncludingSimpleNameOfDeclaringClassInMethodName;
import com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixturePageWithTypeParametersInMethods;
import com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixtureSimplePage;
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

  @Test(expectedExceptions = PageInstantiationException.class, expectedExceptionsMessageRegExp = "Expected to see a public method with signature \\[void doSomethingOnFixturePageWhosImplHasNoMatchingMethod\\(\\)\\] in com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixturePageWhosImplHasNoMatchingMethodImpl")
  public void we_should_throw_an_exception_if_the_page_interface_method_has_no_matching_method_in_the_impl() {
    registerPage(FixturePageWhosImplHasNoMatchingMethod.class);
  }

  @Test(expectedExceptions = PageInstantiationException.class, expectedExceptionsMessageRegExp = "Expected to see a public method with signature \\[void doSomethingOnFixtureComponentWithAMethodNotMatchedInImpl\\(\\)\\] in com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixtureComponentWithAMethodNotMatchedInImplImpl")
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

  @Test(expectedExceptions = PageInstantiationException.class, expectedExceptionsMessageRegExp = "The following methods failed to include the name of their declaring class in their name:\\n  com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixturePageWithMethodNotIncludingSimpleNameOfDeclaringClassInMethodName: doAasd")
  public void we_should_throw_an_exception_if_a_page_contains_a_method_with_a_name_not_including_the_simple_name_of_its_declaring_class() {
    registerPage(FixturePageWithMethodNotIncludingSimpleNameOfDeclaringClassInMethodName.class);
  }

  @Test(expectedExceptions = PageInstantiationException.class, expectedExceptionsMessageRegExp = "Could not load com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixtureComponentWithoutImplImpl\\.  Does it exist\\?")
  public void we_should_throw_an_exception_if_a_page_includes_components_with_no_impl() {
    registerPage(FixturePageThatIncludesAComponentWithNoImpl.class);
  }

  @Test(expectedExceptions = PageInstantiationException.class, expectedExceptionsMessageRegExp = "The following methods accept Type parameters: com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixturePageWithTypeParametersInMethods#doSomethingOnFixturePageWithTypeParametersInMethods, com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixtureComponentWithTypeParametersInMethodName#doSomethingOnFixtureComponentWithTypeParametersInMethodName, com.github.jsdevel.testng.selenium.fixtures.pageproxyfactory.FixtureComponentWithTypeParametersInMethodNameImpl#doSomethingOnFixtureComponentWithTypeParametersInMethodName")
  public void we_should_throw_an_exception_if_a_page_or_any_component_methods_use_type_parameters() {
    registerPage(FixturePageWithTypeParametersInMethods.class);
  }





  // Helpers
  private void registerPage(Class<? extends Browser> clazz) {
    PageProxyFactory.registerPage(clazz, FixturePageFactory.class);
  }
}