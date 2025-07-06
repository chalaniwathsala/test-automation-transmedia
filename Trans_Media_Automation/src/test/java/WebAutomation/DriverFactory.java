package WebAutomation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class DriverFactory {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        return getDriver("chrome");  // default to chrome
    }

    public static WebDriver getDriver(String browserName) {
        if (driver == null) {
            if ("chrome".equalsIgnoreCase(browserName)) {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                driver = new ChromeDriver(options);
            } else {
                // Can add support for other browsers later
                throw new RuntimeException("Unsupported browser: " + browserName);
            }
            // Set timeouts for stability
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.err.println("Error quitting driver: " + e.getMessage());
            } finally {
                driver = null;
            }
        }
    }

    public static WebDriver getCurrentDriver() {
        return driver;
    }
}
