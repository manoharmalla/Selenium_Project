package tests;

import base.BaseClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseClass {

    @BeforeMethod
    public void setUp(){
        launchApplication();
    }

    @Test
    public void VerifyLogin() throws InterruptedException {
        LoginPage lp= new LoginPage(driver);
        lp.moveToLogin();
        Thread.sleep(10000);
    }

    @AfterMethod
    public void tearDown() {
        super.closeApplication();
    }
}
