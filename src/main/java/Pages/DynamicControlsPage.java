package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utilities.UIActions;

/**
 * DynamicControlsPage — handles async DOM changes triggered by button clicks.
 * After every button click, we wait for the #message element to appear —
 * this is the reliable signal that the async operation has completed.
 * No Thread.sleep() used anywhere.
 */
public class DynamicControlsPage extends UIActions {

    @FindBy(css = "#checkbox-example input[type='checkbox']")
    private WebElement checkbox;

    @FindBy(css = "#checkbox-example button")
    private WebElement toggleCheckboxButton;

    @FindBy(css = "#input-example input[type='text']")
    private WebElement textInput;

    @FindBy(css = "#input-example button")
    private WebElement toggleInputButton;

    @FindBy(id = "message")
    private WebElement statusMessage;

    // Used with isElementPresent() — safer when element may not exist in DOM
    private static final By CHECKBOX_LOCATOR =
            By.cssSelector("#checkbox-example input[type='checkbox']");

    public DynamicControlsPage() {
        super();
    }

    public void openPage() {
        navigateTo(getBaseUrl() + "/dynamic_controls");
    }

    // ─── Checkbox actions ─────────────────────────────────────────────────────

    public void clickToggleCheckbox() {
        click(toggleCheckboxButton, "Toggle Checkbox button");
        // Wait for #message — this confirms the async DOM operation is done
        waitForVisibility(statusMessage, 15, "Status message after checkbox toggle");
    }

    public boolean isCheckboxPresent() {
        return isElementPresent(CHECKBOX_LOCATOR);
    }

    // ─── Text input actions ───────────────────────────────────────────────────

    public void clickToggleInput() {
        click(toggleInputButton, "Toggle Input button");
        waitForVisibility(statusMessage, 15, "Status message after input toggle");
    }

    public void waitForInputToBeEnabled() {
        waitForElementEnabled(textInput, 15, "Text input");
    }

    public void waitForInputToBeDisabled() {
        waitForElementDisabled(textInput, 15, "Text input");
    }

    public boolean isTextInputEnabled() {
        return textInput.isEnabled();
    }

    public void typeInTextInput(String text) {
        sendKeys(textInput, text, "Text input field");
    }

    // ─── Status message ───────────────────────────────────────────────────────

    public String getStatusMessage() {
        waitForVisibility(statusMessage, 15, "Status message");
        return getText(statusMessage, "Status message");
    }

    public boolean isStatusMessageDisplayed() {
        return isDisplayed(statusMessage, 5);
    }
}