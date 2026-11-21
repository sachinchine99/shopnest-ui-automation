package Test;
import BaseTestPkg.BaseTest;
import Pages.LoginPage;
import Pages.SecureAreaPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void openPage() {
        loginPage = new LoginPage();
        loginPage.openLoginPage();
    }

    @Test(description = "Valid credentials should redirect to the Secure Area page")
    public void validLogin_shouldRedirectToSecurePage() {
        loginPage.login(
                config.getProperty("valid.username"),
                config.getProperty("valid.password")
        );
        SecureAreaPage securePage = new SecureAreaPage();
        Assert.assertTrue(securePage.isOnSecurePage(),
                "Expected to land on Secure Area after valid login.");
    }

    @Test(description = "Invalid username should show an error flash message")
    public void invalidUsername_shouldShowError() {
        loginPage.login("wronguser", config.getProperty("valid.password"));
        Assert.assertTrue(loginPage.isFlashMessageDisplayed(),
                "Flash message should appear for invalid username.");
        Assert.assertTrue(loginPage.isErrorMessageShown(),
                "Flash message should indicate invalid credentials.");
    }

    @Test(description = "Invalid password should show an error flash message")
    public void invalidPassword_shouldShowError() {
        loginPage.login(config.getProperty("valid.username"), "WrongPassword!");
        Assert.assertTrue(loginPage.isFlashMessageDisplayed(),
                "Flash message should appear for invalid password.");
        Assert.assertTrue(loginPage.isErrorMessageShown(),
                "Flash message should indicate invalid credentials.");
    }

    @Test(description = "Empty username should show an error flash message")
    public void emptyUsername_shouldShowError() {
        loginPage.login("", config.getProperty("valid.password"));
        Assert.assertTrue(loginPage.isFlashMessageDisplayed(),
                "Flash message should appear when username is empty.");
        Assert.assertTrue(loginPage.isErrorMessageShown(),
                "Error should be shown for empty username.");
    }

    @Test(description = "Empty password should show an error flash message")
    public void emptyPassword_shouldShowError() {
        loginPage.login(config.getProperty("valid.username"), "");
        Assert.assertTrue(loginPage.isFlashMessageDisplayed(),
                "Flash message should appear when password is empty.");
        Assert.assertTrue(loginPage.isErrorMessageShown(),
                "Error should be shown for empty password.");
    }
}
