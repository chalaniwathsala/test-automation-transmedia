package AbstractComponents;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;

/**
 * Abstract base class providing common WebDriver utilities and wait methods
 */
public abstract class AbstractComponents {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    
    // Configurable timeouts
    protected static final int DEFAULT_TIMEOUT = 10;
    protected static final int LONG_TIMEOUT = 30;
    protected static final int SHORT_TIMEOUT = 5;
    
    public AbstractComponents(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        this.actions = new Actions(driver);
    }
    
    /**
     * Wait for element to be visible
     */
    public void waitForElementToAppear(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /**
     * Wait for element to be clickable
     */
    public void waitForElementToBeClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    /**
     * Wait for element to disappear
     */
    public void waitForElementToDisappear(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    
    /**
     * Click element with retry logic for stale elements
     */
    public void clickWithRetry(By locator, int maxAttempts) {
        int attempts = 0;
        while (attempts < maxAttempts) {
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                scrollIntoView(element);
                element.click();
                break;
            } catch (StaleElementReferenceException e) {
                attempts++;
                if (attempts >= maxAttempts) {
                    throw new RuntimeException("Failed to click element after " + maxAttempts + " attempts", e);
                }
            }
        }
    }
    
    /**
     * Send keys with retry logic for stale elements
     */
    public void sendKeysWithRetry(By locator, String text, int maxAttempts) {
        int attempts = 0;
        while (attempts < maxAttempts) {
            try {
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                element.clear();
                element.sendKeys(text);
                break;
            } catch (StaleElementReferenceException e) {
                attempts++;
                if (attempts >= maxAttempts) {
                    throw new RuntimeException("Failed to send keys after " + maxAttempts + " attempts", e);
                }
            }
        }
    }
    
    /**
     * Scroll element into view
     */
    public void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
    
    /**
     * Wait for page to load completely
     */
    public void waitForPageLoad() {
        wait.until(driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete"));
    }
    
    /**
     * Get element text safely
     */
    public String getElementText(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
        } catch (TimeoutException e) {
            return "";
        }
    }
    
    /**
     * Check if element is present
     */
    public boolean isElementPresent(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Wait for list count to match expected value
     */
    public boolean waitForListCountToBe(By locator, int expectedCount) {
        try {
            return wait.until(driver -> {
                List<WebElement> elements = driver.findElements(locator);
                return elements.size() == expectedCount;
            });
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Get list count
     */
    public int getListCount(By locator) {
        return driver.findElements(locator).size();
    }
}
