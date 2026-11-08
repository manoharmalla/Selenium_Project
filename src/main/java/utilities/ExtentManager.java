package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    public static ExtentReports getExtentReport() {

        ExtentSparkReporter spark =
                new ExtentSparkReporter("reports/ExtentReport.html");

        ExtentReports extent = new ExtentReports();

        extent.attachReporter(spark);

        return extent;
    }
}
