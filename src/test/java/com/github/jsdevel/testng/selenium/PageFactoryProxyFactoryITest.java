package com.github.jsdevel.testng.selenium;

import com.github.jsdevel.testng.selenium.exceptions.PageFactoryInstantiationException;
import com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory.FixturePageFactory;
import com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory.FixturePageFactoryClass;
import com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory.FixturePageFactoryThatReturnsClasses;
import com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory.FixturePageFactoryThatReturnsNoImpls;
import com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory.FixturePageFactoryWithAMethodThatAcceptsTypeParameters;
import com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory.FixturePageFactoryWithMethodsThatDoNotHaveTheirPageNameInTheirName;
import com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory.FixturePageFactoryWithTypeParameters;
import com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory.FixturePageFactoryWithMethodsThatDoNotReturnAPage;
import com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory.FixturePageFactoryWithMethodsThatReturnPagesWithTypeParameters;
import com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory.FixturePageFactoryWithUnhandledParameterTypes;
import com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory.FixturePageFactoryWithoutDefaultFactoryMethods;
import com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory.FixturePageFactoryWithoutMethods;
import com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory.FixturePageFactoryWithoutProperMethodNames;
import static org.mockito.Mockito.mock;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PageFactoryProxyFactoryITest {
  MethodContext context;

  @BeforeMethod
  public void beforeMethod() {
    context = mock(MethodContext.class);
  }

  @Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = "MethodContext may not be null.")
  public void it_should_throw_an_exception_when_context_is_null() {
    PageFactoryProxyFactory.getPageFactoryProxy(FixturePageFactoryClass.class, null);
  }

  @Test(expectedExceptions = PageFactoryInstantiationException.class, expectedExceptionsMessageRegExp = "Expected com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory.FixturePageFactoryClass to be an interface.")
  public void it_should_throw_an_exception_when_given_a_class() {
    PageFactoryProxyFactory.getPageFactoryProxy(FixturePageFactoryClass.class, context);
  }

  @Test(expectedExceptions = PageFactoryInstantiationException.class, expectedExceptionsMessageRegExp = "com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory.FixturePageFactoryWithMethodsThatDoNotReturnAPage contains the following methods that do not return page instances: getString")
  public void it_should_throw_an_exception_when_given_an_interface_that_has_methods_that_do_not_return_instances_of_Page() {
    PageFactoryProxyFactory.getPageFactoryProxy(FixturePageFactoryWithMethodsThatDoNotReturnAPage.class, context);
  }

  @Test(expectedExceptions = PageFactoryInstantiationException.class, expectedExceptionsMessageRegExp = "PageFactories must declare at least 1 method.  0 methods declared in com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory.FixturePageFactoryWithoutMethods")
  public void it_should_throw_an_exception_if_the_PageFactory_has_no_methods() {
    PageFactoryProxyFactory.getPageFactoryProxy(FixturePageFactoryWithoutMethods.class, context);
  }

  @Test(expectedExceptions = PageFactoryInstantiationException.class, expectedExceptionsMessageRegExp = "The following methods in com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory.FixturePageFactoryWithoutProperMethodNames were not named the name of the page with a prefix of get: geFixtureHomePage")
  public void it_should_throw_an_exception_if_any_method_is_not_a_combination_of_get_plus_the_page_name() {
    PageFactoryProxyFactory.getPageFactoryProxy(FixturePageFactoryWithoutProperMethodNames.class, context);
  }

  @Test(expectedExceptions = PageFactoryInstantiationException.class, expectedExceptionsMessageRegExp = "The following pages have no default factory method \\(no arguments\\) in com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory.FixturePageFactoryWithoutDefaultFactoryMethods: FixtureBooPage, FixtureFooPage")
  public void it_should_throw_an_exception_if_any_page_is_lacking_a_default_factory_method_in_the_PageFactory() {
    PageFactoryProxyFactory.getPageFactoryProxy(FixturePageFactoryWithoutDefaultFactoryMethods.class, context);
  }

  @Test(expectedExceptions = PageFactoryInstantiationException.class, expectedExceptionsMessageRegExp = "No implementations were found for the following pages in com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory.FixturePageFactoryThatReturnsNoImpls: com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory.FixtureBooPage, com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory.FixtureFooPage")
  public void it_should_throw_an_exception_if_the_PageFactory_has_methods_that_return_Pages_with_no_impl() {
    PageFactoryProxyFactory.getPageFactoryProxy(FixturePageFactoryThatReturnsNoImpls.class, context);
  }

  @Test(expectedExceptions = PageFactoryInstantiationException.class, expectedExceptionsMessageRegExp = "Unhandled parameter types in com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory.FixturePageFactoryWithUnhandledParameterTypes for the following methods: getFixtureHomePage")
  public void it_should_throw_an_exception_if_the_PageFactory_has_methods_with_unhandled_parameter_types() {
    PageFactoryProxyFactory.getPageFactoryProxy(FixturePageFactoryWithUnhandledParameterTypes.class, context);
  }

  @Test(expectedExceptions = PageFactoryInstantiationException.class, expectedExceptionsMessageRegExp = "Type Parameters are not supported on PageFactories in com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory.FixturePageFactoryWithTypeParameters")
  public void it_should_throw_an_exception_if_the_PageFactory_defines_generic_parameters() {
    PageFactoryProxyFactory.getPageFactoryProxy(FixturePageFactoryWithTypeParameters.class, context);
  }

  @Test(expectedExceptions = PageFactoryInstantiationException.class, expectedExceptionsMessageRegExp = "Pages are not allowed to accept Type Parameters.  The following pages use them: com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory.FixturePageWithTypeParameters")
  public void it_should_throw_an_exception_if_the_PageFactory_returns_pages_with_type_parameters() {
    PageFactoryProxyFactory.getPageFactoryProxy(FixturePageFactoryWithMethodsThatReturnPagesWithTypeParameters.class, context);
  }

  @Test(expectedExceptions = PageFactoryInstantiationException.class, expectedExceptionsMessageRegExp = "The following methods return non interfaces in com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory.FixturePageFactoryThatReturnsClasses: getFixtureBooPageClass")
  public void it_should_throw_an_exception_if_the_PageFactory_returns_pages_that_are_not_interfaces() {
    PageFactoryProxyFactory.getPageFactoryProxy(FixturePageFactoryThatReturnsClasses.class, context);
  }

  @Test(expectedExceptions = PageFactoryInstantiationException.class, expectedExceptionsMessageRegExp = "The following methods accept Type parameters in com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory.FixturePageFactoryWithAMethodThatAcceptsTypeParameters: getFixtureHomePage\\(\\), getFixtureHomePage\\(String\\)")
  public void it_should_throw_an_exception_if_the_PageFactory_has_methods_that_accept_type_parameters() {
    PageFactoryProxyFactory.getPageFactoryProxy(FixturePageFactoryWithAMethodThatAcceptsTypeParameters.class, context);
  }

  @Test(expectedExceptions = PageFactoryInstantiationException.class, expectedExceptionsMessageRegExp = "The following methods in com.github.jsdevel.testng.selenium.fixtures.pagefactoryproxyfactory.FixturePageFactoryWithMethodsThatDoNotHaveTheirPageNameInTheirName did not contain the name of their page in their name: getFixtureFoo\\(\\)")
  public void it_should_throw_an_exception_if_any_method_names_do_not_contain_the_page_name() {
    PageFactoryProxyFactory.getPageFactoryProxy(FixturePageFactoryWithMethodsThatDoNotHaveTheirPageNameInTheirName.class, context);
  }

  @Test
  public void it_should_return_a_PageFactoryProxy_casted_to_the_generic_PageFactory() {
    FixturePageFactory pageFactoryProxy = PageFactoryProxyFactory.getPageFactoryProxy(FixturePageFactory.class, context);
  }

  @Test
  public void it_should_return_the_same_PageFactory_across_multiple_calls() {
    FixturePageFactory firstPageFactory = PageFactoryProxyFactory.getPageFactoryProxy(FixturePageFactory.class, context);
    FixturePageFactory secondPageFactory = PageFactoryProxyFactory.getPageFactoryProxy(FixturePageFactory.class, context);
    assertTrue(firstPageFactory == secondPageFactory);
  }

}
