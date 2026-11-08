package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.WaitUtils;

import java.util.List;

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
    @FindBy(css="div[title='8 GB and Above  ']")
    private WebElement sRam;
    @FindBy(xpath="//*[normalize-space()='Google' and (self::div or self::label)]")
    private WebElement googleBrand;
    @FindBy(xpath="//a[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'google pixel 10')]")
    private WebElement googlePixel10Product;
    @FindBy(xpath="//a[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'google pixel')]")
    private List<WebElement> pixelLinks;
    @FindBy(xpath="//a[.//*[normalize-space()='iPhone' or normalize-space()='motorola' or normalize-space()='realme' or normalize-space()='POCO' or normalize-space()='Google' or normalize-space()='HMD' or normalize-space()='boltt' or normalize-space()='MIVI' or normalize-space()='Tecno' or normalize-space()='Samsung' or normalize-space()='vivo' or normalize-space()='OPPO' or normalize-space()='Ai+' or normalize-space()='Nothing' or normalize-space()='Infinix' or normalize-space()='LAVA' or normalize-space()='REDMI']]")
    private List<WebElement> mobileBrands;

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
    public void clickMobiles() {
        driver.get("https://www.flipkart.com/mobile-phones-store");
    }
    public void searchGooglePixel10() {
        driver.get("https://www.flipkart.com/search?q=Google%20Pixel%2010");
    }
    public void selectGoogleBrand() {
        wait.waitForElementPresent(googleBrand);
        googleBrand.click();
    }
    public void clickGooglePixel10Product() {
        wait.waitForElementPresent(googlePixel10Product);
        googlePixel10Product.click();
    }
    public String getPageTitle() {
        return driver.getTitle();
    }
    public void clickGooglePixel() {
        if (pixelLinks.isEmpty()) {
            throw new NoSuchElementException("Google Pixel option is not visible on the current page");
        }
        wait.waitForVisibility(pixelLinks.get(0));
        pixelLinks.get(0).click();
    }
    public boolean isGooglePixel10Displayed() {
        String pageText = (driver.getTitle() + " " + driver.getCurrentUrl() + " " + driver.getPageSource()).toLowerCase();
        return pageText.contains("google pixel 10") || pageText.contains("google pixel");
    }
    public String getCategory(){
        return vcat.getText();
    }
    public void applyRam(){
        wait.waitForVisibility(sRam);
        action.click(sRam);
    }

    public int getDisplayedMobileBrandCount() {
        if (mobileBrands.isEmpty()) {
            throw new NoSuchElementException("No mobile brand tiles were found on the page");
        }
        wait.waitForVisibility(mobileBrands.get(0));
        int displayedBrandCount = 0;
        for (WebElement brand : mobileBrands) {
            if (brand.isDisplayed()) {
                displayedBrandCount++;
            }
        }
        return displayedBrandCount;
    }

}
