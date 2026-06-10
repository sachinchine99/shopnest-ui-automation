package utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.time.LocalDateTime;


public class UIActions extends Driver {

    // Called from each Page class constructor to initialise @FindBy locators
    public UIActions() {
        PageFactory.initElements(driver, this);
    }

    // ─── Click ────────────────────────────────────────────────────────────────

    public void click(WebElement el, String errorMessage) {
        try {
            explicitlyWait(el, 15, errorMessage);
            el.click();
            System.out.println("Clicked on: " + errorMessage);
        } catch (TimeoutException e) {
            Assert.fail(errorMessage + " | Element not clickable: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail(errorMessage + " | Unexpected error: " + e.getMessage());
        }
        System.out.println(":-----------------------------------------------------------:");
    }

    // Click only if element is visible — skip silently if not found
    public void clickIfDisplayed(WebElement el, int waitForSec, String errorMessage) {
        try {
            explicitlyWait(el, waitForSec, errorMessage);
            if (el.isDisplayed()) {
                el.click();
                System.out.println("Clicked on: " + errorMessage);
            }
        } catch (Exception e) {
            System.out.println("Element not displayed, skipping: " + errorMessage);
        }
        System.out.println(":-----------------------------------------------------------:");
    }

    // ─── SendKeys ─────────────────────────────────────────────────────────────

    // Added clear() before typing — important for web input fields
    public void sendKeys(WebElement el, String input, String errorMessage) {
        try {
            explicitlyWait(el, 15, errorMessage);
            el.clear();
            el.click();
            el.sendKeys(input);
            System.out.println("Typed '" + input + "' into: " + errorMessage);
        } catch (TimeoutException e) {
            Assert.fail(errorMessage + " | sendKeys failed: " + e.getMessage());
        }
        System.out.println(":-----------------------------------------------------------:");
    }

    // ─── GetText ──────────────────────────────────────────────────────────────

    // Web-specific — returns visible text of an element
    public String getText(WebElement el, String errorMessage) {
        try {
            explicitlyWait(el, 15, errorMessage);
            String text = el.getText().trim();
            System.out.println("Got text from " + errorMessage + ": '" + text + "'");
            return text;
        } catch (TimeoutException e) {
            Assert.fail(errorMessage + " | getText failed: " + e.getMessage());
            return "";
        }
    }

    // ─── isDisplayed ─────────────────────────────────────────────────────────

    public boolean isDisplayed(WebElement el, int waitSec) {
        try {
            explicitlyWait(el, waitSec, "");
            return el.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ─── ExplicitlyWait ───────────────────────────────────────────────────────

    public void explicitlyWait(WebElement el, int durationInSec, String message) {
        LocalDateTime start = LocalDateTime.now();
        System.out.print("Waiting for: " + message + " [" + durationInSec + " sec]");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(durationInSec));
            wait.until(ExpectedConditions.elementToBeClickable(el));
            long millis = Duration.between(start, LocalDateTime.now()).toMillis();
            System.out.print(" | Found in [" + millis + " ms]");
        } catch (TimeoutException e) {
            System.out.print(" | Timeout!");
        }
        System.out.println();
    }

    // Wait for element to be visible — used for flash messages and confirmation text
    public void waitForVisibility(WebElement el, int durationInSec, String message) {
        System.out.print("Waiting for visibility: " + message);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(durationInSec));
            wait.until(ExpectedConditions.visibilityOf(el));
            System.out.println(" | Visible!");
        } catch (TimeoutException e) {
            System.out.println(" | Not visible within " + durationInSec + "s");
        }
    }

    // Wait for element to become enabled — used for Dynamic Controls textbox
    public void waitForElementEnabled(WebElement el, int durationInSec, String message) {
        System.out.print("Waiting for element to be enabled: " + message);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(durationInSec));
            wait.until(d -> el.isEnabled());
            System.out.println(" | Enabled!");
        } catch (TimeoutException e) {
            System.out.println(" | Still disabled after " + durationInSec + "s");
        }
    }

    // Wait for element to become disabled — used after clicking Disable button
    public void waitForElementDisabled(WebElement el, int durationInSec, String message) {
        System.out.print("Waiting for element to be disabled: " + message);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(durationInSec));
            wait.until(d -> !el.isEnabled());
            System.out.println(" | Disabled!");
        } catch (TimeoutException e) {
            System.out.println(" | Still enabled after " + durationInSec + "s");
        }
    }

    // Check if an element exists in the DOM — used for Dynamic Controls checkbox
    public boolean isElementPresent(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ─── Navigate ─────────────────────────────────────────────────────────────

    public void navigateTo(String url) {
        driver.get(url);
        System.out.println("Navigated to: " + url);
        System.out.println(":-----------------------------------------------------------:");
    }
}