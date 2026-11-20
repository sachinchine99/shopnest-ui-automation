package Pages;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.UIActions;
import java.time.Duration;

/**
 * SecureAreaPage — represents the page after a successful login.
 * Extends UIActions — same pattern as your mobile page classes.
 */
public class SecureAreaPage extends UIActions {

    @FindBy(css = "h2")
    private WebElement pageHeading;

    @FindBy(id = "flash")
    private WebElement flashMessage;

    @FindBy(css = "a.button.secondary[href='/logout']")
    private WebElement logoutButton;

    public SecureAreaPage() {
        super();
        // Wait until URL changes to /secure before doing anything else
        // Without this, constructor runs before redirect completes
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlContains("/secure"));
    }

    public void clickLogout() {
        click(logoutButton, "Logout button");
    }

    public String getPageHeadingText() {
        return getText(pageHeading, "Page heading");
    }

    public String getFlashMessageText() {
        waitForVisibility(flashMessage, 10, "Flash message");
        return getText(flashMessage, "Flash message");
    }

    public boolean isOnSecurePage() {
        boolean urlOk      = driver.getCurrentUrl().contains("/secure");
        boolean headingOk  = getPageHeadingText().contains("Secure Area");
        System.out.println("Secure page check — URL: " + urlOk + " | Heading: " + headingOk);
        return urlOk && headingOk;
    }

    public boolean isSuccessFlashDisplayed() {
        return getFlashMessageText().toLowerCase().contains("logged in");
    }

    public boolean isLogoutButtonDisplayed() {
        return isDisplayed(logoutButton, 5);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
