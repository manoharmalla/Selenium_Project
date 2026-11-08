package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.WaitUtils;

import java.util.List;

public class cameraPage {
    private final WebDriver driver;
    private final WaitUtils wait;

    @FindBy(xpath="//a[normalize-space()='Electronics' or .//*[normalize-space()='Electronics']]")
    private WebElement electronics;
    @FindBy(xpath="//*[normalize-space()='Camera']")
    private WebElement camera;
    @FindBy(xpath="//*[normalize-space()='Nikon']")
    private WebElement nikon;
    @FindBy(xpath="//a[normalize-space()='IZI' or .//*[normalize-space()='IZI']]")
    private WebElement izi;
    @FindBy(xpath="//*[normalize-space()='NIKON D780 DSLR Camera Body Only']")
    private WebElement nikonD780Product;
    @FindBy(xpath="//a[contains(@href, '/izi-go-x-new-smartphone-gimbal-stabilizer-oled-display-wireless-charging-pad-3-axis-mobile/')]")
    private WebElement iziGoXProduct;
    @FindBy(xpath="//*[normalize-space()='IZI GO-X New Smartphone Gimbal Stabilizer With OLED Display & Wireless Charging Pad 3 Axis Gimbal for Mobile']")
    private WebElement iziGoXProductTitle;
    @FindBy(css="div.vHZIyH")
    private WebElement loginOverlay;
    @FindBy(css="a[href*='/p/'] img")
    private List<WebElement> productImages;

    public cameraPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    public void dismissLoginPrompt() {
        wait.waitForVisibility(loginOverlay);
        new Actions(driver).sendKeys(Keys.ESCAPE).perform();
    }

    public void clickElectronics() {
        wait.waitForElementPresent(electronics);
        electronics.click();
    }

    public void clickCamera() {
        wait.waitForElementPresent(camera);
        camera.click();
    }

    public void clickNikon() {
        wait.waitForElementPresent(nikon);
        nikon.click();
    }

    public void clickIzi() {
        wait.waitForElementPresent(izi);
        izi.click();
    }

    public void clickNikonD780Product() {
        wait.waitForElementPresent(nikonD780Product);
        nikonD780Product.click();
    }

    public void clickIziGoXProduct() {
        String currentWindow = driver.getWindowHandle();
        wait.waitForElementPresent(iziGoXProduct);
        iziGoXProduct.click();
        driver.switchTo().window(wait.waitForNewWindow(currentWindow));
        wait.waitForTitleContains("IZI GO-X New Smartphone Gimbal Stabilizer");
    }

    public String getNikonD780ProductText() {
        wait.waitForVisibility(nikonD780Product);
        return nikonD780Product.getText();
    }

    public String getProductPageTitle() {
        return driver.getTitle();
    }

    public boolean isIziGoXProductDisplayed() {
        wait.waitForVisibility(iziGoXProductTitle);
        return iziGoXProductTitle.getText()
            .equals("IZI GO-X New Smartphone Gimbal Stabilizer With OLED Display & Wireless Charging Pad 3 Axis Gimbal for Mobile");
    }

    public int getDisplayedProductImageCount() {
        int displayedImageCount = 0;
        for (WebElement productImage : productImages) {
            if (productImage.isDisplayed()) {
                displayedImageCount++;
            }
        }
        return displayedImageCount;
    }
}