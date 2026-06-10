# ShopNest UI Automation Framework

Selenium + TestNG automation suite for the ShopNest QA Assignment.
**Target site:** https://the-internet.herokuapp.com

---

## Tech Stack

| Tool | Version |
|---|---|
| Java | 17 |
| Selenium WebDriver | 4.21 |
| WebDriverManager | 5.8 |
| TestNG | 7.10 |
| Maven | 3.9+ |
| Extent Reports | 5.1 |

---

## Project Structure

```
src/main/java/com/shopnest/
  base/
    Driver.java          — browser setup, extent report, config loading
    UIActions.java       — reusable web actions (click, type, wait, etc.)
  pages/
    LoginPage.java
    SecureAreaPage.java
    DynamicControlsPage.java
    FileUploadPage.java

src/test/java/com/shopnest/
  base/
    BaseTest.java        — @BeforeSuite, @BeforeMethod, @AfterMethod hooks
  tests/
    LoginTest.java
    FormAuthTest.java
    DynamicControlsTest.java
    FileUploadTest.java

src/test/resources/
  config/config.properties
  testdata/upload_file.txt
```

---

## How to Run

```bash
# Run full suite
mvn clean test

# Run headless (for CI)
mvn clean test -Dheadless=true

# Run on Edge
mvn clean test -Dbrowser=edge
```

---

## Reports

- Extent HTML Report → `Extent Report/ShopNest_Report.html`
- Open in any browser after the run

---

## Test Coverage

| Test Class | Scenarios |
|---|---|
| LoginTest | Valid login, invalid username, invalid password, empty username, empty password |
| FormAuthTest | Redirect to /secure, heading verification, success flash, logout |
| DynamicControlsTest | Checkbox remove/add, input enable/disable |
| FileUploadTest | Upload success, filename match, confirmation header |