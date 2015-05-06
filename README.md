java-testng-selenium
======================
> A library for easy TestNG Selenium testing.

This library exposes many annotations that can make testing with selenium a breeze
with TestNG.  Being that TestNG supports dependency injection, it is very easy to
do this.

## High level benefits of using this library

* Use many annotations in conjunction with `@Test`
* Unified configuration for WebDriver instances via annotations.
* Configure defaults via system properties I.E. `-Dtestng.selenium.screensize=1200x400`
* Extend your suite classes with `AbstractSuite` to take advantage of a test context and running test methods in parallel.
* Emailable reports and screenshots are handled out of the box.
