package Test;

import BaseTestPkg.BaseTest;
import Pages.LoginPage;
import Pages.SecureAreaPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FormAuthTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void openPage() {
        loginPage = new LoginPage();
        loginPage.openLoginPage();
    }

    @Test(description = "After login, URL should contain /secure")
    public void successfulLogin_shouldRedirectToSecureUrl() {
        loginPage.login(
                config.getProperty("valid.username"),
                config.getProperty("valid.password")
        );
        SecureAreaPage securePage = new SecureAreaPage();
        Assert.assertTrue(securePage.getCurrentUrl().contains("/secure"),
                "URL should contain /secure after login. Actual: " + securePage.getCurrentUrl());
    }

    @Test(description = "Secure area page should display the correct heading")
    public void successfulLogin_shouldShowSecureAreaHeading() {
        loginPage.login(
                config.getProperty("valid.username"),
                config.getProperty("valid.password")
        );
        SecureAreaPage securePage = new SecureAreaPage();
        Assert.assertTrue(securePage.isOnSecurePage(),
                "Page heading should read 'Secure Area'. Actual: " + securePage.getPageHeadingText());
    }

    @Test(description = "A success flash message should appear after login")
    public void successfulLogin_shouldShowSuccessFlash() {
        loginPage.login(
                config.getProperty("valid.username"),
                config.getProperty("valid.password")
        );
        SecureAreaPage securePage = new SecureAreaPage();
        Assert.assertTrue(securePage.isSuccessFlashDisplayed(),
                "Success flash message should be visible. Actual: " + securePage.getFlashMessageText());
    }

    @Test(description = "Logout should redirect back to the login page")
    public void logout_shouldReturnToLoginPage() {
        loginPage.login(
                config.getProperty("valid.username"),
                config.getProperty("valid.password")
        );
        SecureAreaPage securePage = new SecureAreaPage();
        Assert.assertTrue(securePage.isLogoutButtonDisplayed(), "Logout button should be visible.");

        securePage.clickLogout();

        LoginPage returnedPage = new LoginPage();
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"),
                "After logout, URL should return to /login.");
        Assert.assertTrue(returnedPage.getFlashMessageText().toLowerCase().contains("logged out"),
                "Flash message should confirm logout.");
    }
}