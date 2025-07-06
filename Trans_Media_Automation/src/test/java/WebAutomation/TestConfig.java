package WebAutomation;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration class for test settings
 */
public class TestConfig {
    
    private static final Properties properties = new Properties();
    private static final String CONFIG_FILE = "src/test/resources/test.properties";
    
    // Default values
    private static final String DEFAULT_BASE_URL = "http://localhost:3000/";
    private static final String DEFAULT_BROWSER = "chrome";
    private static final int DEFAULT_TIMEOUT = 30;
    private static final int DEFAULT_IMPLICIT_WAIT = 10;
    
    static {
        loadProperties();
    }
    
    /**
     * Load properties from configuration file
     */
    private static void loadProperties() {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
        } catch (IOException e) {
            System.out.println("Configuration file not found, using default values: " + e.getMessage());
        }
    }
    
    /**
     * Get property value with default fallback
     */
    private static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * Get integer property value with default fallback
     */
    private static int getIntProperty(String key, int defaultValue) {
        String value = properties.getProperty(key);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer value for " + key + ", using default: " + defaultValue);
            }
        }
        return defaultValue;
    }
    
    /**
     * Get base URL for tests
     */
    public static String getBaseUrl() {
        return getProperty("base.url", DEFAULT_BASE_URL);
    }
    
    /**
     * Get browser type for tests
     */
    public static String getBrowser() {
        return getProperty("browser", DEFAULT_BROWSER);
    }
    
    /**
     * Get timeout value in seconds
     */
    public static int getTimeout() {
        return getIntProperty("timeout", DEFAULT_TIMEOUT);
    }
    
    /**
     * Get implicit wait value in seconds
     */
    public static int getImplicitWait() {
        return getIntProperty("implicit.wait", DEFAULT_IMPLICIT_WAIT);
    }
    
    /**
     * Get headless mode setting
     */
    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "false"));
    }
    
    /**
     * Get screenshot on failure setting
     */
    public static boolean isScreenshotOnFailure() {
        return Boolean.parseBoolean(getProperty("screenshot.on.failure", "true"));
    }
    
    /**
     * Get screenshot directory
     */
    public static String getScreenshotDir() {
        return getProperty("screenshot.dir", "test-output/screenshots");
    }
    
    /**
     * Get test data directory
     */
    public static String getTestDataDir() {
        return getProperty("test.data.dir", "src/test/resources/testdata");
    }
    
    /**
     * Get report directory
     */
    public static String getReportDir() {
        return getProperty("report.dir", "test-output/reports");
    }
} 