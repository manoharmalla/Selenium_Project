package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.WaitUtils;

public class homePage {
    private final WebDriver driver;
    private final WaitUtils wait;
    public homePage(WebDriver driver){
        this.driver=driver;
        this.wait = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }
    @FindBy(name="q")
    private WebElement search;

    public void SearchMobile(String mobile){
        wait.waitForElementPresent(search);
        search.sendKeys(mobile);
        search.sendKeys(Keys.ENTER);
    }

}
