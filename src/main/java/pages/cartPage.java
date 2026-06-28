package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.WaitUtils;

public class cartPage {
    private final WebDriver driver;
    private final WaitUtils wait;
    private final Actions action;

    @FindBy(css="img[alt='Cart']")
    private WebElement cart;
    @FindBy(xpath = "//div[text()='Missing Cart items?']")
    private WebElement cText;

    public cartPage(WebDriver driver){
        this.driver= driver;
        this.wait= new WaitUtils(driver);
        this.action=new Actions(driver);
        PageFactory.initElements(driver, this);
    }
    public void clickCart(){
        wait.waitForVisibility(cart);
        action.click(cart);
    }
    public String getCartText(){
        wait.waitForVisibility(cText);
        return cart.getText();
    }

}
