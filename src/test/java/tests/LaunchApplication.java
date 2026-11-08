package tests;

import base.BaseClass;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.homePage;
import utilities.configReader;

public class LaunchApplication extends BaseClass {

    @BeforeMethod
    public void setUp(){
        launchApplication();
    }

    @Test
    public void verifyApplicatioLaunch(){
        System.out.println("Application has launched successfully");
    }
    @Test
    public void verifySearchMobile() throws InterruptedException {
        homePage hm= new homePage(driver);
        hm.SearchMobile(configReader.getProperty("mobile"));
    }
    @Test
    public void ApplyMobileCat(){
        homePage hm= new homePage(driver);
        hm.SearchMobile(configReader.getProperty("mobile"));
        System.out.println(configReader.getProperty("mobile"));
        hm.applyMobileCat();
    }
    @Test
    public void verifyCategory() throws InterruptedException {
        homePage hm = new homePage(driver);
        hm.clickMobiles();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("mobile-phones-store") || currentUrl.contains("search"), "Mobile category page did not open");
    }
    @Test
    public void verifySeventeenMobileBrandsAreDisplayed() {
        homePage hm = new homePage(driver);
        hm.clickMobiles();
        Assert.assertEquals(hm.getDisplayedMobileBrandCount(), 17,
                "Expected all 17 mobile brand tiles to be displayed");
    }
    @Test
    public void verifyGooglePixel10DisplayedInUI() {
        homePage hm = new homePage(driver);
        hm.clickMobiles();
        hm.selectGoogleBrand();
        hm.searchGooglePixel10();
        hm.clickGooglePixel10Product();
        Assert.assertTrue(hm.getPageTitle().toLowerCase().contains("google pixel 10"),
                "Product page title did not identify Google Pixel 10: " + hm.getPageTitle());
    }

    @AfterMethod
    public void tearDown(){
        closeApplication();
    }

}
