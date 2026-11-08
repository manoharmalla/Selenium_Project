package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.ActionUtils;
import utilities.WaitUtils;

public class LoginPage {
    private WebDriver driver;
    private Actions action;
    private ActionUtils au;
    private WaitUtils wait;
    @FindBy(xpath = "//span[text()='Login']")
    private WebElement login;

    public LoginPage(WebDriver driver  ){
        this.driver=driver;
        this.wait= new WaitUtils(driver);
        this.action = new Actions(driver);
        this.au= new ActionUtils(driver,wait, action);
        PageFactory.initElements(driver,this);
    }
    public void moveToLogin(){
        au.moveToElement(login);
    }

}
