package tests;

import base.BaseClass;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.homePage;
import utilities.configReader;

public class LaunchApplication extends BaseClass {

    @BeforeTest
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
        homePage hm= new homePage(driver);
        hm.SearchMobile(configReader.getProperty("mobile"));
        hm.applyMobileCat();
        hm.getCategory();
        String category = hm.getCategory();
        Assert.assertEquals(category, "Mobiles", "Mobile category is not applied");
        hm.applyRam();
        Thread.sleep(3000);
    }

    @AfterTest
    public void tearDown(){
        closeApplication();
    }

}
