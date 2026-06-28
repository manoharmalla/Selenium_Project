package tests;

import base.BaseClass;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.cartPage;

public class CartTest extends BaseClass {
    @BeforeTest
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
    @AfterTest
    public void tearDown(){
        closeApplication();
    }
}
