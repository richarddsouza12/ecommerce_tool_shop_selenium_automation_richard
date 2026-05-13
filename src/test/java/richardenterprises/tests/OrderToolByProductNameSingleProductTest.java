package richardenterprises.tests;

import com.aventstack.extentreports.ExtentTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import richardenterprises.page_objects.*;
import richardenterprises.recources.ConfigManager;
import richardenterprises.recources.ExtentTestManager;
import richardenterprises.recources.Utils;
import richardenterprises.test_components.BaseTest;

import java.io.IOException;
import java.time.Duration;

public class OrderToolByProductNameSingleProductTest extends BaseTest {

    /* Base Test
          public WebDriver driver;
     */
    public String productsTestDataString = """
            
            [
                 {
                     "productDetails" : {
                         "productId" : "01KRDFS58EG76PZQM88MGKDFFC",
                         "productName" : "Bolt Cutters"
                     },
                     "customerBasicDetails" : {
                        "type" : "guest",
                        "firstName" : "Billy",
                        "lastName" : "Halo",
                        "email" : "billyhalo@gmail.com"
                     } ,
                     "customerBillingDetails" : {
                        "pinCode" : "411012",
                        "houseNo" : "111",
                        "street" : "Miller Street",
                        "city" : "Panjim",
                        "state" : "Goa"
                     }
                 },
                 {
                     "productDetails" : {
                         "productId" : "01KRDFS58A33NQ3QWW0WJCRNRC",
                         "productName" : "Combination Pliers"
                     },
                     "customerBasicDetails" : {
                        "type" : "guest",
                        "firstName" : "Bob",
                        "lastName" : "Jason",
                        "email" : "bobjason@gmail.com"
                     },
                     "customerBillingDetails" : {
                        "pinCode" : "411012",
                        "houseNo" : "111",
                        "street" : "Miller Street",
                        "city" : "Panjim",
                        "state" : "Goa"
                     }
            
                 }
            ]
            
            """;

    public OrderToolByProductNameSingleProductTest() throws IOException {
        super.initializeWebDriver();
    }

    @Test
    public void OrderSingleToolByProductNameCashOnDeliveryE2ETest() throws Exception {

        ExtentTest threadSafeExtentTest = ExtentTestManager.getTest();
        ConfigManager configManager = ConfigManager.createOrGetInstance();

        //implicit wait set for 5 seconds.
        WebDriverWait webDriverWaitExplicit;
        webDriverWaitExplicit = new WebDriverWait(driver, Duration.ofSeconds(10));

        //TestData
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode productsTestDataArrayNode = objectMapper.readValue( this.productsTestDataString , ArrayNode.class );
        JsonNode productTestDataJsonNode = productsTestDataArrayNode.get(0);
        System.out.println( productTestDataJsonNode.get("productDetails").get("productName").asText());


        this.driver.get( configManager.getBaseUrl() );

        threadSafeExtentTest.info("Browser Launched");
        threadSafeExtentTest.info("url : " +  configManager.getBaseUrl() );

        //Nav Bar ===============================
        NavBarSectionPage navBarSectionPage = new NavBarSectionPage(this.driver);


        //Home Page ===============================
        HomePage homePage = new HomePage( this.driver );
        homePage.selectProductByProductName(  productTestDataJsonNode.get("productDetails").get("productName").asText() );

        //reporting
        ExtentTest testReportNode = threadSafeExtentTest.createNode("Products Home Page");
        testReportNode.info("Selected a product");
        testReportNode.info( productTestDataJsonNode.get("productDetails").get("productName").asText() );
        String prettyJson = Utils.getPrettyStringFromAnyDataObject(objectMapper, productTestDataJsonNode);
        testReportNode.info(   "<pre>" + prettyJson + "</pre>");


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
        testReportNode.addScreenCaptureFromPath( screenshotPath );
        testReportNode.info("Clicked the cart icon");


        //Nav Section
        navBarSectionPage.clickCartIcon();

        // Cart Page ===============================
        Utils.waitForPageReadyState(webDriverWaitExplicit);
        CartPage cartPage = new CartPage(this.driver);
        cartPage.checkItemInCartByName( productTestDataJsonNode.get("productDetails").get("productName").asText() );

        testReportNode = threadSafeExtentTest.createNode("Cart Page");
        testReportNode.info("Checking for the item present in cart");
        testReportNode.info( productTestDataJsonNode.get("productDetails").get("productName").asText() );
        screenshotPath = Utils.getScreenshot("Cart Page", Utils.getCurrentDateTimeTimestampString(), this.driver);
        testReportNode.addScreenCaptureFromPath( screenshotPath );

        cartPage.clickProceedToCheckout();

        // Checkout Page ============================
        //=============================================
        Utils.waitForPageReadyState(webDriverWaitExplicit);
        CheckoutPage checkoutPage = new CheckoutPage(this.driver);

        testReportNode = threadSafeExtentTest.createNode("Checkout Page");

        checkoutPage.fillGuestCustomerBasicDetails(
                productTestDataJsonNode.get("customerBasicDetails").get("email").asText(),
                productTestDataJsonNode.get("customerBasicDetails").get("firstName").asText(),
                productTestDataJsonNode.get("customerBasicDetails").get("lastName").asText()
        );

        //reporting
        testReportNode.info("Filling Guest Basic Details");
        prettyJson = Utils.getPrettyStringFromAnyDataObject(objectMapper, productTestDataJsonNode.get("customerBasicDetails"));
        testReportNode.info(   "<pre>" + prettyJson + "</pre>");

        //Billing Details
        checkoutPage.fillBillingDetails(
                productTestDataJsonNode.get("customerBillingDetails").get("pinCode").asText(),
                productTestDataJsonNode.get("customerBillingDetails").get("houseNo").asText(),
                productTestDataJsonNode.get("customerBillingDetails").get("street").asText(),
                productTestDataJsonNode.get("customerBillingDetails").get("city").asText(),
                productTestDataJsonNode.get("customerBillingDetails").get("state").asText()
        );

        //reporting
        testReportNode.info("Filling Guest Basic Details");
        prettyJson = Utils.getPrettyStringFromAnyDataObject(objectMapper, productTestDataJsonNode.get("customerBillingDetails"));
        testReportNode.info(   "<pre>" + prettyJson + "</pre>");

        //Payment Method
        checkoutPage.selectPaymentMethodCashOnDeliveryAndCompleteIt();
        testReportNode.info("Selecting Cash on Delivery Payment Method");

        //Order Completion
        String orderId  = checkoutPage.viewOrderCompletionPage();
        testReportNode.info("Order Completed - Id :" + orderId );
    }



}