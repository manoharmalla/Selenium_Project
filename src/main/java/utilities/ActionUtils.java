package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ActionUtils {
    private final WebDriver driver;
    private final WaitUtils wait;
    private final Actions action;

    public ActionUtils(WebDriver driver, WaitUtils wait, Actions action){
        this.driver= driver;
        this.action= new Actions(driver);
        this.wait = new WaitUtils(driver);

    }
    public void click(WebElement element){
        wait.waitForVisibility(element);
        element.click();
    }

}
