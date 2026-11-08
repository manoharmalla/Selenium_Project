package tests;

import base.BaseClass;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.cartPage;

public class CartTest extends BaseClass {
    @BeforeMethod
    public void setUp(){
        launchApplication();
    }
    @Test
    public void verifyCart() throws InterruptedException {
        cartPage cp = new cartPage(driver);
        cp.clickCart();
    }
    @Test
    public void verifyCartText() throws InterruptedException {
        cartPage cp = new cartPage(driver);
        cp.clickCart();

    }
    @AfterMethod
    public void tearDown(){
        closeApplication();
    }
}
