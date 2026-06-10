package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utilities.UIActions;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * FileUploadPage — handles file upload on /upload page.
 *
 * Strategy: sendKeys(absoluteFilePath) directly on the <input type="file"> element.
 * This bypasses the OS file dialog entirely — works in headless mode and CI pipelines.
 */
public class FileUploadPage extends UIActions {

    @FindBy(id = "file-upload")
    private WebElement fileInput;

    @FindBy(id = "file-submit")
    private WebElement uploadButton;

    @FindBy(id = "uploaded-files")
    private WebElement uploadedFileName;

    @FindBy(css = "div.example h3")
    private WebElement confirmationHeader;

    public FileUploadPage() {
        super();
    }

    public void openPage() {
        navigateTo(getBaseUrl() + "/upload");
    }

    // Resolves file from src/test/resources and sends its path to the file input
    public void uploadFileFromClasspath(String resourcePath) {
        File file = resolveFile(resourcePath);
        System.out.println("Uploading file: " + file.getAbsolutePath());
        fileInput.sendKeys(file.getAbsolutePath()); // No OS dialog needed
        System.out.println(":-----------------------------------------------------------:");
    }

    public void clickUploadButton() {
        click(uploadButton, "Upload submit button");
        waitForVisibility(uploadedFileName, 15, "Uploaded file name");
    }

    // ─── Assertion helpers ────────────────────────────────────────────────────

    public String getUploadedFileName() {
        return getText(uploadedFileName, "Uploaded file name");
    }

    public String getConfirmationHeaderText() {
        return getText(confirmationHeader, "Confirmation header");
    }

    public boolean isUploadSuccessful() {
        return isDisplayed(uploadedFileName, 10);
    }

    // ─── Private helper ───────────────────────────────────────────────────────

    private File resolveFile(String resourcePath) {
        URL url = getClass().getClassLoader().getResource(resourcePath);
        if (url == null) {
            throw new RuntimeException("File not found on classpath: " + resourcePath);
        }
        try {
            return new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid URI for resource: " + resourcePath, e);
        }
    }
}