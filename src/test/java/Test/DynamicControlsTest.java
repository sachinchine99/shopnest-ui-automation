package Test;

import BaseTestPkg.BaseTest;
import Pages.DynamicControlsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DynamicControlsTest extends BaseTest {

    private DynamicControlsPage page;

    @BeforeMethod(alwaysRun = true)
    public void openPage() {
        page = new DynamicControlsPage();
        page.openPage();
    }

    @Test(description = "Checkbox should be present on page load")
    public void checkboxShouldBePresentOnLoad() {
        Assert.assertTrue(page.isCheckboxPresent(),
                "Checkbox should be visible when the page first loads.");
    }

    @Test(description = "Clicking Remove should remove the checkbox from the DOM")
    public void clickRemove_shouldRemoveCheckbox() {
        page.clickToggleCheckbox();
        Assert.assertFalse(page.isCheckboxPresent(),
                "Checkbox should be removed after clicking Remove.");
        Assert.assertTrue(page.getStatusMessage().toLowerCase().contains("gone"),
                "Status message should say it's gone. Actual: " + page.getStatusMessage());
    }

    @Test(description = "Clicking Add after Remove should restore the checkbox")
    public void clickAdd_shouldRestoreCheckbox() {
        page.clickToggleCheckbox(); // Remove
        Assert.assertFalse(page.isCheckboxPresent(), "Checkbox should be gone after Remove.");

        page.clickToggleCheckbox(); // Add back
        Assert.assertTrue(page.isCheckboxPresent(),
                "Checkbox should be restored after clicking Add.");
        Assert.assertTrue(page.getStatusMessage().toLowerCase().contains("back"),
                "Status message should say it's back. Actual: " + page.getStatusMessage());
    }

    @Test(description = "Text input should be disabled on page load")
    public void textInputShouldBeDisabledOnLoad() {
        Assert.assertFalse(page.isTextInputEnabled(),
                "Text input should be disabled when the page first loads.");
    }

    @Test(description = "Clicking Enable should enable the text input")
    public void clickEnable_shouldEnableTextInput() {
        page.clickToggleInput();
        page.waitForInputToBeEnabled();
        Assert.assertTrue(page.isTextInputEnabled(),
                "Text input should be enabled after clicking Enable.");
        Assert.assertTrue(page.getStatusMessage().toLowerCase().contains("enabled"),
                "Status message should confirm enabled. Actual: " + page.getStatusMessage());
    }

    @Test(description = "Clicking Disable after Enable should disable the text input again")
    public void clickDisable_shouldDisableTextInput() {
        page.clickToggleInput(); // Enable
        page.waitForInputToBeEnabled();

        page.typeInTextInput("QA Automation Test");

        page.clickToggleInput(); // Disable
        page.waitForInputToBeDisabled();
        Assert.assertFalse(page.isTextInputEnabled(),
                "Text input should be disabled after clicking Disable.");
        Assert.assertTrue(page.getStatusMessage().toLowerCase().contains("disabled"),
                "Status message should confirm disabled. Actual: " + page.getStatusMessage());
    }
}