package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.WaitUtils;

public class homePage {
    private final WebDriver driver;
    private final WaitUtils wait;
    private final Actions action;
    //search tab
    @FindBy(name="q")
    private WebElement search;
    //mobile in filters
    @FindBy(css=  "a[title='Mobiles']")
    private WebElement fmobile;
    @FindBy(css="a[title='Mobiles']")
    private WebElement vcat;
    @FindBy(css="div[title='8 GB and Above']")
    private WebElement sRam;

    public homePage(WebDriver driver){
        this.driver=driver;
        this.wait = new WaitUtils(driver);
        this.action = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    public void SearchMobile(String mobile){
        wait.waitForElementPresent(search);
        search.sendKeys(mobile);
        search.sendKeys(Keys.ENTER);
    }
    public void applyMobileCat(){
        wait.waitForVisibility(fmobile);
        action.click(fmobile);
    }
    public String getCategory(){
        return vcat.getText();
    }
    public void applyRam(){
        wait.waitForVisibility(sRam);
        action.click(sRam);
    }

}
