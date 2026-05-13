package richardenterprises.tests;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import richardenterprises.page_objects.*;
import richardenterprises.recources.ConfigManager;
import richardenterprises.recources.ExtentTestManager;
import richardenterprises.recources.Utils;
import richardenterprises.test_components.BaseTest;
import java.io.IOException;
import java.time.Duration;

public class OrderToolsE2EFirstProductTest extends BaseTest {

    /* Base Test
          public WebDriver driver;
     */

    public OrderToolsE2EFirstProductTest() throws IOException {
        super.initializeWebDriver();
    }

    @Test
    public void AddToCartSingleToolFirstProductTest() throws Exception {

        ExtentTest threadSafeExtentTest = ExtentTestManager.getTest();
        ConfigManager configManager = ConfigManager.createOrGetInstance();

        //implicit wait set for 5 seconds.
        WebDriverWait webDriverWaitExplicit;
        webDriverWaitExplicit = new WebDriverWait(driver, Duration.ofSeconds(10));

        this.driver.get(configManager.getBaseUrl());
        threadSafeExtentTest.info("Browser Launched");
        threadSafeExtentTest.info("url : " + configManager.getBaseUrl());

        //Nav Bar ===============================
        NavBarSectionPage navBarSectionPage = new NavBarSectionPage(this.driver);


        //Home Page ===============================
        HomePage homePage = new HomePage(this.driver);
        homePage.selectFirstProduct();

        //reporting
        ExtentTest testReportNode = threadSafeExtentTest.createNode("Products Home Page");
        testReportNode.info("Selected First Product");


        //Product Page ===============================
        //wait till page load
        Utils.waitForPageReadyState(webDriverWaitExplicit);
        ProductDescriptionPage prodDescPage = new ProductDescriptionPage(this.driver);
        prodDescPage.addDisplayedProductToCart();

        //reporting
        testReportNode = threadSafeExtentTest.createNode("Product Details Page");
        testReportNode.info("Add Item to Cart");
        testReportNode.info("Waiting for the toast message");
        String screenshotPath = Utils.getScreenshot("Product Page", Utils.getCurrentDateTimeTimestampString(), this.driver);
        testReportNode.addScreenCaptureFromPath(screenshotPath);
        testReportNode.info("Clicked the cart icon");


        //Nav Section
        navBarSectionPage.clickCartIcon();

        // Cart Page ===============================
        Utils.waitForPageReadyState(webDriverWaitExplicit);
        CartPage cartPage = new CartPage(this.driver);
        cartPage.checkIfCartNotEmpty();

        //reporting
        testReportNode = threadSafeExtentTest.createNode("Cart Page");
        testReportNode.info("Checking for the items present in cart");
        screenshotPath = Utils.getScreenshot("Cart Page", Utils.getCurrentDateTimeTimestampString(), this.driver);
        testReportNode.addScreenCaptureFromPath(screenshotPath);

    }

}