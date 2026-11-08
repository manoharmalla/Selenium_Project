package tests;

import base.BaseClass;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.cameraPage;

public class CameraProductTest extends BaseClass {
    @BeforeMethod
    public void setUp() {
        launchApplication();
    }

    @Test
    public void verifyNikonD780ProductText() {
        cameraPage cameraPage = new cameraPage(driver);
        cameraPage.dismissLoginPrompt();
        cameraPage.clickElectronics();
        cameraPage.clickCamera();
        cameraPage.clickNikon();
        cameraPage.clickNikonD780Product();

        Assert.assertEquals(cameraPage.getNikonD780ProductText(),
                "NIKON D780 DSLR Camera Body Only",
                "Product text did not match the selected Nikon camera");
    }

    @Test
    public void verifyCameraImagesAreDisplayedOnPage() {
        cameraPage cameraPage = new cameraPage(driver);
        cameraPage.dismissLoginPrompt();
        cameraPage.clickElectronics();
        cameraPage.clickCamera();

        Assert.assertTrue(cameraPage.getDisplayedProductImageCount() > 0,
                "No camera product images are displayed on the Camera page");
    }

    @AfterMethod
    public void tearDown() {
        closeApplication();
    }
}