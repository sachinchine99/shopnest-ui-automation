package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

/**
 * Driver.java — Web version of your existing mobile Driver class.
 * Same structure and static field pattern as your mobile Driver.java.
 * Manages browser launch, quit, config loading, and Extent Report setup.
 */
public class Driver {

    public static WebDriver driver;
    public static ExtentReports report;
    public static ExtentTest test;
    public static ExtentSparkReporter spark;
    public static Properties config;

    // ─── Load config from config.properties ──────────────────────────────────

    public static void loadConfig() {
        try {
            config = new Properties();
            FileInputStream fis = new FileInputStream(
                    "src/test/resources/config/config.properties");
            config.load(fis);
            System.out.println("Config loaded successfully.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties: " + e.getMessage());
        }
    }

    // ─── Browser launch ───────────────────────────────────────────────────────

    public static void launchBrowser() {
        if (config == null) loadConfig();

        String browser = System.getProperty("browser", config.getProperty("browser", "chrome")).toLowerCase();
        boolean headless = Boolean.parseBoolean(
                System.getProperty("headless", config.getProperty("headless", "false")));

        System.out.println("Browser launch: " + browser + " | Headless: " + headless);

        if (browser.equals("edge")) {
            WebDriverManager.edgedriver().setup();
            EdgeOptions opts = new EdgeOptions();
            if (headless) opts.addArguments("--headless=new");
            opts.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--window-size=1920,1080");
            driver = new EdgeDriver(opts);
        } else {
            // Default: Chrome
            WebDriverManager.chromedriver().setup();
            ChromeOptions opts = new ChromeOptions();
            if (headless) opts.addArguments("--headless=new");
            opts.addArguments("--no-sandbox", "--disable-dev-shm-usage",
                    "--window-size=1920,1080", "--remote-allow-origins=*");
            driver = new ChromeDriver(opts);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0)); // Explicit waits use karu
        System.out.println("Browser launched successfully!");
        System.out.println(":-----------------------------------------------------------:");
    }

    // ─── Driver quit ──────────────────────────────────────────────────────────

    // Tuzya mobile Driver.java madhe Thread.sleep(5000) hota — web madhe garj nahi
    public static void quitDriver() {
        if (driver != null) {
            try {
                driver.quit();
                System.out.println("Driver quit successfully.");
            } catch (Exception e) {
                System.out.println("Driver quit madhe exception: " + e.getMessage());
            } finally {
                driver = null;
            }
        } else {
            System.out.println("Driver already null ahe.");
        }
        System.out.println(":-----------------------------------------------------------:");
    }

    // ─── Extent Report — same as your mobile framework ────────────────────────

    public static void initializeExtentReport() {
        if (report == null) {
            report = new ExtentReports();
            // Report folder automatically create hoil
            new File("Extent Report").mkdirs();
            String reportPath = "Extent Report" + File.separator + "ShopNest_Report.html";
            spark = new ExtentSparkReporter(reportPath);
            spark.config().setDocumentTitle("ShopNest UI Automation");
            spark.config().setReportName("Web Regression Suite");
            report.attachReporter(spark);
            System.out.println("Extent Report initialized: " + reportPath);
        }
    }

    public static ExtentTest createExtentTestLog(ITestResult result) {
        test = report.createTest(result.getMethod().getMethodName())
                .assignCategory(result.getTestClass().getName())
                .assignAuthor("Sachin")
                .assignDevice(System.getProperty("os.name"));
        return test;
    }

    // Screenshot on failure — same as your mobile attachScreenshotToReport()
    public static void attachScreenshotToReport(ExtentTest test) {
        try {
            String base64Screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BASE64);
            test.fail("Test failed — Screenshot:",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
        } catch (Exception e) {
            test.fail("Screenshot capture failed: " + e.getMessage());
        }
    }

    // ─── Helper: Base URL get karo ────────────────────────────────────────────

    public static String getBaseUrl() {
        if (config == null) loadConfig();
        return config.getProperty("base.url", "https://the-internet.herokuapp.com");
    }
}