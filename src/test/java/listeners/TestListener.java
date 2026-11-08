package listeners;

import base.BaseClass;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utilities.ExtentManager;
import utilities.ScreenshotUtils;

public class TestListener implements ITestListener {
    ExtentReports extent = ExtentManager.getExtentReport();

    ExtentTest test;

    @Override
    public void onTestFailure(ITestResult result){
       String name = result.getName();
        ScreenshotUtils.captureScreenShot(BaseClass.getDriver(), name );
        test.fail(result.getThrowable());
    }
    @Override
    public void onTestStart(ITestResult result){
        test = extent.createTest(result.getName());
    }
    @Override
    public void onTestSuccess(ITestResult result) {

        test.pass("Test Passed");


    }

    @Override
    public void onFinish(ITestContext context) {

        extent.flush();

    }
}
