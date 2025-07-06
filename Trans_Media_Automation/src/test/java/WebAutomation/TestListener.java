package WebAutomation;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestListener implements ITestListener {
    
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("🚀 Starting test: " + result.getName());
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("✅ Test passed: " + result.getName());
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("❌ Test failed: " + result.getName());
        
        if (TestConfig.isScreenshotOnFailure()) {
            takeScreenshot(result.getName());
        }
        
        // Print exception details
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            System.err.println("Exception: " + throwable.getMessage());
            throwable.printStackTrace();
        }
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("⏭️ Test skipped: " + result.getName());
    }
    
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("⚠️ Test failed but within success percentage: " + result.getName());
    }
    
    @Override
    public void onStart(ITestContext context) {
        System.out.println("🎯 Starting test suite: " + context.getName());
        createDirectories();
    }
    
    @Override
    public void onFinish(ITestContext context) {
        System.out.println("🏁 Finished test suite: " + context.getName());
        System.out.println("📊 Test Results Summary:");
        System.out.println("   Passed: " + context.getPassedTests().size());
        System.out.println("   Failed: " + context.getFailedTests().size());
        System.out.println("   Skipped: " + context.getSkippedTests().size());
    }
    
    /**
     * Take screenshot on test failure
     */
    private void takeScreenshot(String testName) {
        try {
            WebDriver driver = DriverFactory.getCurrentDriver();
            if (driver != null && driver instanceof TakesScreenshot) {
                String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
                String fileName = testName + "_" + timestamp + ".png";
                String screenshotDir = TestConfig.getScreenshotDir();
                Path screenshotPath = Paths.get(screenshotDir, fileName);
                
                // Create screenshot directory if it doesn't exist
                Files.createDirectories(screenshotPath.getParent());
                
                // Take screenshot
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                Files.copy(screenshot.toPath(), screenshotPath);
                
                System.out.println("📸 Screenshot saved: " + screenshotPath.toAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
        }
    }
    
    /**
     * Create necessary directories
     */
    private void createDirectories() {
        try {
            // Create screenshot directory
            Path screenshotDir = Paths.get(TestConfig.getScreenshotDir());
            Files.createDirectories(screenshotDir);
            
            // Create report directory
            Path reportDir = Paths.get(TestConfig.getReportDir());
            Files.createDirectories(reportDir);
            
            // Create test data directory
            Path testDataDir = Paths.get(TestConfig.getTestDataDir());
            Files.createDirectories(testDataDir);
            
        } catch (IOException e) {
            System.err.println("Failed to create directories: " + e.getMessage());
        }
    }
} 