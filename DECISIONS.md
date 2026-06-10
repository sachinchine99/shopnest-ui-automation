# DECISIONS.md

## Framework Design Decisions

---

## 1. Language and Framework Choice

**Java 17 + Selenium WebDriver 4 + TestNG**

I chose Java because it is the industry standard for enterprise UI automation. Most product companies and startups in India use Java-based Selenium frameworks for their QA teams.

Selenium WebDriver 4 is used because it follows the W3C protocol and WebDriverManager automatically handles driver binary downloads — no manual chromedriver setup needed.

TestNG is chosen over JUnit because it supports parallel execution, group-based test runs via testng.xml, and has better reporting integration with Extent Reports.

---

## 2. Design Pattern — Page Object Model (POM)

All locators and page interactions are kept inside Page classes only. Test classes never contain any WebElement or By locator directly.

This means if a locator changes on the UI, we only update it in one place — the Page class. Nothing breaks in the test classes.

---

## 3. Framework Structure — Mobile Framework Style

The framework is structured similar to a mobile Appium framework:

- `Driver.java` — manages browser launch, quit, config loading, and Extent Report setup
- `UIActions.java` — extends Driver, provides reusable methods like click(), sendKeys(), explicitlyWait()
- Page classes extend `UIActions` — so every page has access to all reusable actions directly

This keeps the code flat, easy to read, and easy to explain.

---

## 4. No Thread.sleep()

Thread.sleep() is not used anywhere in the framework. All waits use Selenium's WebDriverWait with ExpectedConditions.

For Dynamic Controls page specifically, after every button click we wait for the `#message` element to appear — this is the real DOM signal that the async operation completed, not a timer guess.

---

## 5. Configuration via config.properties

All environment settings (base URL, browser, credentials) are stored in `config.properties`. This means changing the browser or URL does not require any code change — just update the properties file.

CI can also override values by passing `-Dbrowser=chrome -Dheadless=true` as Maven system properties.

---

## 6. Extent Reports Only

Only Extent Reports is used for HTML reporting. Allure was intentionally removed to keep the setup simple and dependency count low. Extent generates a single self-contained HTML file that can be shared with anyone without any server setup.

Screenshot on failure is automatically attached to the Extent report via BaseTest's @AfterMethod.

---

## 7. File Upload Strategy

File upload uses `sendKeys(absoluteFilePath)` directly on the `<input type="file">` element. This completely bypasses the OS file dialog and works in headless mode and Linux CI runners without any additional tooling.

---

## 8. What I Would Add with More Time

- **Data-driven testing** using TestNG @DataProvider for login scenarios with multiple credential sets
- **Retry mechanism** for handling occasional network flakiness on the demo site
- **Cross-browser matrix** in GitHub Actions to run Chrome and Edge in parallel
- **Cucumber BDD layer** on top of existing page objects for business-readable test scenarios
- **Docker Selenium Grid** for fully portable execution without local browser installation