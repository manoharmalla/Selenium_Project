package tests;

import base.BaseClass;
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
    public void verifySearchMobile(){
        homePage hm= new homePage(driver);
        hm.SearchMobile(configReader.getProperty("mobile"));
        System.out.println(configReader.getProperty("mobile"));
    }

    @AfterTest
    public void tearDown(){
        closeApplication();
    }

}
