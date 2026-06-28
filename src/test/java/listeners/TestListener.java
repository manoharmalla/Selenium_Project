package listeners;

import base.BaseClass;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utilities.ScreenshotUtils;

public class TestListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result){
       String name = result.getName();
        ScreenshotUtils.captureScreenShot(BaseClass.getDriver(), name );
    }
}
