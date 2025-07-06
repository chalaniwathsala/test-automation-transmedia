package WebAutomation.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;

    public static synchronized ExtentReports getExtentReports() {
        if (extent == null) {
            // Where the report file will be created
            String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport.html";
            
            // Create the Spark reporter
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setDocumentTitle("Automation Report");
            spark.config().setReportName("Functional Tests");
            
            // Attach to ExtentReports and set system info
            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        }
        return extent;
    }
}
