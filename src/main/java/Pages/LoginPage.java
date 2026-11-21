package Pages;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.UIActions;

/**
 * LoginPage — extends UIActions, same pattern as your mobile page classes.
 *
 * All locators are private @FindBy fields.
 * All interactions go through UIActions methods (click, sendKeys, getText).
 * Test classes only call the public methods defined here — no WebElements in tests.
 */
public class LoginPage extends UIActions {

    // ─── Locators ─────────────────────────────────────────────────────────────

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    @FindBy(id = "flash")
    private WebElement flashMessage;

    // ─── Constructor ──────────────────────────────────────────────────────────

    public LoginPage() {
        super(); // calls UIActions() → PageFactory.initElements(driver, this)
    }

    // ─── Navigation ───────────────────────────────────────────────────────────

    public void openLoginPage() {
        navigateTo(getBaseUrl() + "/login");
    }

    // ─── Actions ──────────────────────────────────────────────────────────────

    public void enterUsername(String username) {
        sendKeys(usernameInput, username, "Username field");
    }

    public void enterPassword(String password) {
        sendKeys(passwordInput, password, "Password field");
    }

    public void clickLoginButton() {
        click(loginButton, "Login button");
    }

    // Single method to perform full login
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    // ─── Assertion helpers ────────────────────────────────────────────────────

    public String getFlashMessageText() {
        waitForVisibility(flashMessage, 10, "Flash message");
        return getText(flashMessage, "Flash message");
    }

    public boolean isFlashMessageDisplayed() {
        return isDisplayed(flashMessage, 5);
    }

    public boolean isErrorMessageShown() {
        String msg = getFlashMessageText().toLowerCase();
        return msg.contains("invalid") || msg.contains("error");
    }
}
