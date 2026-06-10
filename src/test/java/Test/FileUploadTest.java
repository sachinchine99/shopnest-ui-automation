package Test;

import BaseTestPkg.BaseTest;
import Pages.FileUploadPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FileUploadTest extends BaseTest {

    private static final String UPLOAD_RESOURCE  = "testdata/upload_file.txt";
    private static final String EXPECTED_FILENAME = "upload_file.txt";

    private FileUploadPage page;

    @BeforeMethod(alwaysRun = true)
    public void openPage() {
        page = new FileUploadPage();
        page.openPage();
    }

    @Test(description = "Uploading a file should show the success confirmation element")
    public void uploadFile_shouldShowSuccessConfirmation() {
        page.uploadFileFromClasspath(UPLOAD_RESOURCE);
        page.clickUploadButton();
        Assert.assertTrue(page.isUploadSuccessful(),
                "Upload confirmation element should be visible after a successful upload.");
    }

    @Test(description = "Uploaded filename shown on page should match the file that was uploaded")
    public void uploadFile_shouldDisplayCorrectFilename() {
        page.uploadFileFromClasspath(UPLOAD_RESOURCE);
        page.clickUploadButton();
        Assert.assertEquals(page.getUploadedFileName(), EXPECTED_FILENAME,
                "Displayed filename should match the uploaded file.");
    }

    @Test(description = "Confirmation header should read 'File Uploaded!' after upload")
    public void uploadFile_shouldShowConfirmationHeader() {
        page.uploadFileFromClasspath(UPLOAD_RESOURCE);
        page.clickUploadButton();
        Assert.assertTrue(page.getConfirmationHeaderText().contains("File Uploaded"),
                "Header should say 'File Uploaded!'. Actual: " + page.getConfirmationHeaderText());
    }
}