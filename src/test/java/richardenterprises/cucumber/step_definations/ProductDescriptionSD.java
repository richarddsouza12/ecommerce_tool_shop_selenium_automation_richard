package richardenterprises.cucumber.step_definations;

import com.aventstack.extentreports.ExtentTest;
import io.cucumber.java.en.Given;
import org.openqa.selenium.support.ui.WebDriverWait;
import richardenterprises.cucumber.StepDefinationContextSession;
import richardenterprises.page_objects.ProductDescriptionPage;
import richardenterprises.recources.ConfigManager;
import richardenterprises.recources.ExtentTestManager;
import richardenterprises.recources.Utils;

import java.io.IOException;
import java.time.Duration;

public class ProductDescriptionSD {

    public ConfigManager configManager;
    public WebDriverWait webDriverWaitExplicit;
    public StepDefinationContextSession SDSess;

    public ProductDescriptionSD( StepDefinationContextSession stepDefinationContextSession ) {

        this.SDSess = stepDefinationContextSession;

        this.configManager = ConfigManager.createOrGetInstance();

        //implicit wait set for 5 seconds.
        this.webDriverWaitExplicit = new WebDriverWait( this.SDSess.baseTest.driver, Duration.ofSeconds(10));


    }

    @Given("Land on product description page and add product to cart")
    public void land_on_product_description_page_and_add_product_to_cart() throws IOException {

        //Product Page ===============================
        //wait till page load
        Utils.waitForPageReadyState(this.webDriverWaitExplicit);
        ProductDescriptionPage prodDescPage = new ProductDescriptionPage(this.SDSess.baseTest.driver);
        prodDescPage.addDisplayedProductToCart();

        //reporting
        ExtentTest threadSafeExtentTest = ExtentTestManager.getTest();
        ExtentTest testReportNode = threadSafeExtentTest.createNode("Product Details Page");
        testReportNode.info("Add Item to Cart");
        testReportNode.info("Waiting for the toast message");
        String screenshotPath = Utils.getScreenshotForCucumberExtentReport("Product Page", Utils.getCurrentDateTimeTimestampString(), this.SDSess.baseTest.driver);
        testReportNode.addScreenCaptureFromPath( screenshotPath );
        testReportNode.info("Clicked the cart icon");


    }



}
