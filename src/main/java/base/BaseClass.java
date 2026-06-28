package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseClass {
    public static WebDriver driver;
    public void launchApplication(){
        driver= new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.flipkart.com/");

    }
    public static WebDriver getDriver(){
        return driver;
    }

    public void closeApplication(){
        if (driver != null) {
            driver.quit();
        }
    }
    public String getTitle(){
        return driver.getTitle();
    }
}
