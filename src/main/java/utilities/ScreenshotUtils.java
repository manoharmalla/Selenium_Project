package utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class ScreenshotUtils {
    public static String captureScreenShot(WebDriver driver, String name){
        String path = System.getProperty("user.dir")
                + "/screenshots/"
                + name
                + "_"
                + System.currentTimeMillis()
                + ".png";

        File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try{
            FileUtils.copyFile(src, new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path;
    }
}
