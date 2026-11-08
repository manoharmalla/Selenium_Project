# Selenium Project Knowledge

This document records the repository state observed on 2026-11-07. Source code and build configuration are authoritative; this document should be rechecked when those files change.

## 1. Project Overview

This is a Java UI automation project for Flipkart, using Selenium WebDriver, TestNG, and Gradle. Tests inherit browser lifecycle methods from `base.BaseClass`. Page-specific locators and actions are grouped in classes under `pages`, and shared helpers live under `utilities`.

The README calls the project a hybrid automation framework combining POM, data-driven testing, and TestNG. The code does use page objects, TestNG, and a properties-based value, but no Excel-driven or `@DataProvider` implementation was found. The term “hybrid” in the README should not be read as evidence of features absent from source.

## 2. Technology Stack and Versions

Verified from `build.gradle` and the Gradle wrapper configuration:

| Technology | Version or configuration | Repository evidence |
| --- | --- | --- |
| Java | No source/target compatibility or toolchain version is pinned in `build.gradle` | `build.gradle` |
| Gradle | Wrapper distribution `8.14` | `gradle/wrapper/gradle-wrapper.properties` |
| Selenium WebDriver | `4.45.0` (`selenium-java`) | `build.gradle` |
| TestNG | `7.11.0` | `build.gradle` |
| WebDriverManager | `6.1.0` | `build.gradle`; no source usage found |
| Apache POI | `poi` and `poi-ooxml` `5.4.1` | `build.gradle`; no workbook usage found |
| ExtentReports | `5.1.2` | `build.gradle` |
| Log4j2 | `log4j-api` and `log4j-core` `2.25.1` | `build.gradle`; no logger usage/config found |
| Commons IO | `2.20.0` | `build.gradle`; used by screenshot copying |
| Gson | `2.13.1` | `build.gradle`; no source usage found |

The Gradle Java plugin is used. Maven Central is the configured dependency repository. ExtentReports is declared in both `implementation` and `testImplementation` configurations.

## 3. Project Folder Structure

Relevant source and configuration paths:

```text
build.gradle
settings.gradle
gradlew
gradlew.bat
gradle/wrapper/gradle-wrapper.properties
README.md
tescases.file
.github/
  agents/
    selenium-agent.md
    selenium-project-test.agent.md
  knowledge-base/
    project-guide.md
    rag-retrieval.md
knowledge-base/
  selenium-project-knowledge.md
src/
  main/
    java/
      base/BaseClass.java
      pages/homePage.java
      pages/cartPage.java
      pages/LoginPage.java
      pages/cameraPage.java
      utilities/ActionUtils.java
      utilities/WaitUtils.java
      utilities/configReader.java
      utilities/ExtentManager.java
      utilities/ScreenshotUtils.java
    resources/config.properties
  test/
    java/
      listeners/TestListener.java
      tests/LaunchApplication.java
      tests/CartTest.java
      tests/LoginTest.java
      tests/CameraProductTest.java
    resources/testng.xml
reports/ExtentReport.html
screenshots/
build/                       generated Gradle output, reports, and test results
bin/                          compiled/copied output; not the source of truth
```

The testcase file is currently spelled `tescases.file` (not `testcases.file`). `src/` is the authoritative Java source tree. `build/` and `bin/` contain generated or copied output and should not be treated as source implementations. Other `.github` knowledge and agent files are repository guidance, not executable framework code.

## 4. Framework Architecture

The current call structure is:

1. A TestNG test class extends `BaseClass` and invokes its browser setup method in a TestNG configuration method.
2. The test creates a page object using the shared `driver`.
3. The page object owns locators, interactions, and calls to shared utilities where implemented.
4. The test performs assertions using TestNG.
5. TestNG teardown invokes `BaseClass.closeApplication()`.

`TestListener` can create Extent entries and failure screenshots when it is loaded by a TestNG run. The Gradle `test` task calls `useTestNG()` but does not select `src/test/resources/testng.xml`; therefore the XML suite and its listener should not be assumed to control ordinary `gradlew test` executions.

## 5. WebDriver Initialization

`base.BaseClass.launchApplication()` in `src/main/java/base/BaseClass.java` directly creates a Selenium `ChromeDriver`, maximizes its window, and opens `https://www.flipkart.com/`.

No `WebDriverManager` call, driver factory, browser selection setting, remote driver configuration, explicit Chrome options, or WebDriver thread-local is implemented in this method. `WebDriverManager` is a declared dependency only. Tests therefore require a working Chrome/ChromeDriver environment and network access to Flipkart. The Gradle build does not pin the Java runtime version.

The driver field is `public static`, so it is shared globally by test instances. No parallel-isolation strategy is implemented; parallel execution could cause tests to interfere with each other's browser state.

## 6. BaseClass Responsibilities

`base.BaseClass` currently provides:

- `launchApplication()`: create and maximize Chrome, then navigate to Flipkart.
- `getDriver()`: return the static driver.
- `closeApplication()`: call `quit()` if the driver is non-null.
- `getTitle()`: return the current browser title.

Concrete test classes own the TestNG setup/teardown annotations and call these methods. `BaseClass` itself does not declare TestNG lifecycle annotations.

## 7. Page Object Model Implementation

Page classes are under `src/main/java/pages/` and initialize fields with `PageFactory.initElements(driver, this)`:

- `homePage`: search, mobile category, RAM filter, Google brand, and Pixel-related elements and methods. `clickMobiles()` and `searchGooglePixel10()` currently navigate using hard-coded URLs rather than clicking/typing through the UI.
- `cartPage`: cart element interactions and a cart-page text method.
- `LoginPage`: the Login element and `moveToLogin()`, which delegates to `ActionUtils`.
- `cameraPage`: Electronics, Camera, Nikon, Nikon D780, and login-overlay elements. It exposes click methods, `dismissLoginPrompt()`, and `getNikonD780ProductText()`.

Page classes generally accept `WebDriver` in their constructor and instantiate `WaitUtils`. The class naming is inconsistent (`homePage`, `cartPage`, and `cameraPage` use lower camel case, while `LoginPage` uses upper camel case). New code should follow the established package boundaries and avoid duplicating browser lifecycle in page objects.

## 8. Locator Strategies

The inspected page objects use Selenium `@FindBy` with CSS selectors, `name`, and XPath selectors. `PageFactory` initializes those fields. Examples include the search field by `name`, the cart image by CSS `alt`, and user-visible text using XPath.

No `By`-based locator factory or `data-testid` convention was identified in the inspected page objects. Some XPath expressions are broad text matches, and the camera login overlay uses the CSS class selector `div.vHZIyH`; these may be sensitive to application markup changes. There is no centralized locator repository. Locators should be checked against the live application before being reused or generalized.

## 9. TestNG Execution

Test classes under `src/test/java/tests/` are:

- `LaunchApplication`: `verifyApplicatioLaunch`, `verifySearchMobile`, `ApplyMobileCat`, `verifyCategory`, and `verifyGooglePixel10DisplayedInUI`.
- `CartTest`: `verifyCart` and `verifyCartText`.
- `LoginTest`: `VerifyLogin`.
- `CameraProductTest`: `verifyNikonD780ProductText`.

The tests use TestNG annotations, including `@Test`, `@BeforeTest`/`@AfterTest`, and `@BeforeClass`/`@AfterClass`. They extend `BaseClass` and construct page objects with `driver`.

`src/test/resources/testng.xml` defines an `Automation Suite`, registers `listeners.TestListener`, and lists the four test classes above. However, the `test` task in `build.gradle` only calls `useTestNG()` and does not configure this XML as a suite file. Gradle test discovery and explicit TestNG XML suite execution are distinct; inspect or change the build configuration before claiming the XML listener is used by `gradlew test`.

## 10. Test Data Management

`src/main/resources/config.properties` currently defines a `mobile` property. `LaunchApplication` reads it through `configReader.getProperty("mobile")` for mobile search/category test steps.

Apache POI is declared in Gradle, but source search found no `Workbook`, `Sheet`, `XSSFWorkbook`, Excel reader, or TestNG `@DataProvider` implementation. No `.xls`, `.xlsx`, or CSV test data files were found. Therefore, Excel-driven testing is not currently implemented; the README's “Data-Driven Testing” description overstates the source implementation.

Other scenario data, such as the Nikon product name and Google Pixel search string, is hard-coded in test/page-object code.

## 11. Configuration Management

`utilities.configReader` loads `src/main/resources/config.properties` from a path relative to the process working directory during static initialization and exposes `getProperty(String key)`.

Limitations:

- The resource is opened using a hard-coded relative filesystem path rather than the classpath.
- An `IOException` is printed with `printStackTrace()`; loading failure can leave `properties` null.
- Missing property keys are not validated and can return null.
- No environment-specific configuration, command-line override, or secret-management mechanism is implemented in this class.

Do not copy secret or credential values into generated documentation. The observed `mobile` setting is ordinary test input, not a credential.

## 12. Reporting and Logging

Reporting implementation:

- `utilities.ExtentManager.getExtentReport()` creates an `ExtentReports` instance with an `ExtentSparkReporter` targeting `reports/ExtentReport.html`.
- `listeners.TestListener` creates an Extent test in `onTestStart`, marks success in `onTestSuccess`, records the throwable in `onTestFailure`, and flushes the report in `onFinish`.
- On failure, the listener calls `ScreenshotUtils.captureScreenShot()`.
- Gradle's TestNG integration generates its HTML test report under `build/reports/tests/test/`.

The listener does not attach the screenshot path to the Extent test, and it has no explicit skipped-test callback. Its `ExtentTest test` field is shared and no parallel-safe test association is implemented.

Logging is not implemented with Log4j in source. Although Log4j API/Core are declared dependencies, no logger calls or Log4j configuration file were found. `LaunchApplication` uses `System.out.println()` for simple console messages. Do not describe structured logging as an existing feature.

## 13. Screenshot Handling

`utilities.ScreenshotUtils.captureScreenShot(WebDriver driver, String name)`:

- Captures a PNG using Selenium's `TakesScreenshot` and `OutputType.FILE`.
- Copies it with Apache Commons IO to `screenshots/<name>_<timestamp>.png` under `user.dir`.
- Returns the saved path and wraps copy `IOException` in `RuntimeException`.

`TestListener.onTestFailure()` calls this utility using the test method name. The returned path is not attached to the Extent report. Screenshot capture depends on a valid active driver and writable output path.

## 14. Reusable Utility Methods

- `utilities.WaitUtils`: creates a `WebDriverWait` with a 20-second timeout. `waitForVisibility(WebElement)` waits for visibility; `waitForElementPresent(WebElement)` actually waits for clickability, despite its name. The stored driver field is not otherwise used. There are no waits for invisibility, text, URL, or locator-based conditions.
- `utilities.ActionUtils.click(WebElement)`: waits for visibility and calls `element.click()`.
- `utilities.ActionUtils.moveToElement(WebElement)`: waits for visibility and calls `action.moveToElement(element)` but never calls `.perform()`, so the hover is not executed.
- `utilities.configReader.getProperty(String)`: returns a property loaded from the relative properties path described above.
- `utilities.ScreenshotUtils.captureScreenShot(WebDriver, String)`: saves and returns the timestamped screenshot path.
- `utilities.ExtentManager.getExtentReport()`: creates the Extent report object and attaches its Spark reporter.

`ActionUtils` accepts `WaitUtils` and `Actions` parameters but ignores them, creating new instances instead. `cartPage.clickCart()` similarly calls `action.click(cart)` without `.perform()`, so the click is not executed through that Actions chain. `cartPage.getCartText()` waits for `cText` but returns `cart.getText()` instead; this is likely a defect in the current implementation.

## 15. Existing Coding Conventions

- Production Java packages are `base`, `pages`, and `utilities`; test packages are `tests` and `listeners`.
- Tests extend `BaseClass`, start/stop the browser in TestNG lifecycle methods, instantiate a page object with the shared driver, and use TestNG `Assert` where assertions are present.
- Page classes use `@FindBy` and `PageFactory.initElements()` for element fields.
- Explicit waits are provided through `WaitUtils`; fixed sleeps also exist in `LoginTest` and should not be copied as the preferred pattern.
- Method and class naming is mixed and sometimes inconsistent in capitalization. Preserve compatibility when touching existing APIs; use clear conventional names for new code when possible.
- There is no formatter, linter, or Java style configuration declared in `build.gradle`.

Current test-quality caveats:

- `LaunchApplication.verifyApplicatioLaunch()` only prints a message and has no assertion.
- `CartTest.verifyCart()` and `verifyCartText()` have no assertions; `cartPage.clickCart()` does not perform its Actions click.
- `LoginTest.VerifyLogin()` sleeps for 10 seconds and does not assert an outcome; its hover helper does not perform the action.
- `homePage.isGooglePixel10Displayed()` searches title, URL, and the entire page source and accepts generic “google pixel” text, which is weaker than a product-specific assertion.
- `CameraProductTest.verifyNikonD780ProductText()` asserts exact text and dismisses a login overlay by waiting for `div.vHZIyH` then sending Escape. The overlay class and UI text locators may change with Flipkart.

## 16. Test Execution Commands

Run all tests discovered by Gradle from the repository root:

```powershell
.\gradlew.bat test
```

Run a single test class:

```powershell
.\gradlew.bat test --tests tests.CameraProductTest
```

Run all tests on macOS/Linux:

```sh
./gradlew test
```

The wrapper uses Gradle `8.14`. Execution requires a valid Java installation, a compatible Chrome/ChromeDriver setup, and network access to Flipkart. `build.gradle` does not pin the Java version or configure Gradle to use `testng.xml` as a suite file.

## 17. Existing Framework Limitations

- Chrome is the only browser initialized by `BaseClass`; cross-browser support is not implemented.
- WebDriverManager is present as a dependency but is not invoked.
- The driver is static and shared; parallel test isolation is not implemented.
- The Gradle `test` task uses TestNG but does not explicitly consume the TestNG XML suite.
- Apache POI is declared but there is no Excel reader, workbook test data, or TestNG data provider.
- Log4j is declared but there is no logger implementation or logging configuration.
- Several page interactions use hard-coded navigation or broad text-based XPath locators.
- `ActionUtils.moveToElement()` omits `.perform()`; cart click and cart text retrieval contain the issues described above.
- `configReader` depends on the working directory and does not validate load failure or missing values.
- Some tests have no meaningful assertions, and `LoginTest` uses a fixed sleep.
- Failure screenshots are not linked from the Extent report, and skipped tests are not explicitly recorded by the listener.
- UI behavior depends on Flipkart markup, modal state, network availability, and browser environment; locator success is not guaranteed by compilation.
- The README and `.github/knowledge-base/project-guide.md` contain stale or broader claims. For example, the guide's page/test inventory omits `cameraPage` and `CameraProductTest`, and describes older Vivo T5 Pro coverage. Verify documentation against current source before generating code.
- `.github/agents/selenium-agent.md` refers to `selenium-framework.agent.md` as its primary reference, but no file with that name exists in the repository. `.vscode/mcp.json` exists, but its values are intentionally not reproduced here. Agent-specific instructions should be checked against available tools and current source rather than treated as proof of implemented framework features.