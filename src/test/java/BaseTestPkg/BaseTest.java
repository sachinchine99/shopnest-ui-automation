package BaseTestPkg;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utilities.Driver;

public class BaseTest extends Driver {

    @BeforeSuite(alwaysRun = true)
    public void suiteSetup() {
        loadConfig();
        initializeExtentReport();
        System.out.println("===== SUITE STARTED =====");
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp(ITestResult result) {
        System.out.println("----- Test START: " + result.getMethod().getMethodName() + " -----");
        launchBrowser();
        createExtentTestLog(result);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("Test FAILED: " + result.getMethod().getMethodName());
            attachScreenshotToReport(test);
            test.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test PASSED");
        } else {
            test.skip("Test SKIPPED");
        }
        quitDriver();
        System.out.println("----- Test END: " + result.getMethod().getMethodName() + " -----\n");
    }

    @AfterSuite(alwaysRun = true)
    public void suiteTeardown() {
        if (report != null) {
            report.flush();
            System.out.println("Extent Report saved.");
        }
        System.out.println("===== SUITE ENDED =====");
    }
}
