package tests;

import base.BaseClass;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.cameraPage;

public class IziGimbalTest extends BaseClass {
    private static final String PRODUCT_NAME = "IZI GO-X New Smartphone Gimbal Stabilizer With OLED Display & Wireless Charging Pad 3 Axis Gimbal for Mobile";

    @BeforeMethod
    public void setUp() {
        launchApplication();
    }

    @Test
    public void verifyIziGoXProductText() {
        cameraPage camera = new cameraPage(driver);
        camera.dismissLoginPrompt();
        camera.clickElectronics();
        camera.clickCamera();
        camera.clickIzi();
        camera.clickIziGoXProduct();

        Assert.assertTrue(camera.isIziGoXProductDisplayed(),
            "The expected IZI gimbal product is not displayed on the product page: " + PRODUCT_NAME);
    }

    @Test
    public void verifyIziGoXMobileIsDisplayedInUI() {
        cameraPage camera = new cameraPage(driver);
        camera.dismissLoginPrompt();
        camera.clickElectronics();
        camera.clickCamera();
        camera.clickIzi();
        camera.clickIziGoXProduct();

        Assert.assertTrue(camera.isIziGoXProductDisplayed(),
                "The IZI GO-X mobile product is not displayed in the UI");
    }

    @AfterMethod
    public void tearDown() {
        closeApplication();
    }
}