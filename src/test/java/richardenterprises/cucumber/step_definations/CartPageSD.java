package richardenterprises.cucumber.step_definations;

import com.aventstack.extentreports.ExtentTest;
import io.cucumber.java.en.Given;
import org.openqa.selenium.support.ui.WebDriverWait;
import richardenterprises.cucumber.StepDefinationContextSession;
import richardenterprises.page_objects.CartPage;
import richardenterprises.recources.ConfigManager;
import richardenterprises.recources.ExtentTestManager;
import richardenterprises.recources.Utils;

import java.io.IOException;
import java.time.Duration;

public class CartPageSD {


    public ConfigManager configManager;
    public WebDriverWait webDriverWaitExplicit;
    public StepDefinationContextSession SDSess;
    public CartPage cartPage;

    public CartPageSD( StepDefinationContextSession stepDefinationContextSession ) {

        this.SDSess = stepDefinationContextSession;

        this.configManager = ConfigManager.createOrGetInstance();

        //implicit wait set for 5 seconds.
        this.webDriverWaitExplicit = new WebDriverWait( this.SDSess.baseTest.driver, Duration.ofSeconds(10));

        this.cartPage = new CartPage(this.SDSess.baseTest.driver);

    }

    @Given("Check if items present in cart")
    public void check_if_items_present_in_cart() throws IOException {

        // Cart Page ===============================
        Utils.waitForPageReadyState(webDriverWaitExplicit);
        this.cartPage.checkIfCartNotEmpty();

        //reporting
        ExtentTest threadSafeExtentTest = ExtentTestManager.getTest();
        ExtentTest testReportNode = threadSafeExtentTest.createNode("Cart Page");
        testReportNode.info("Checking for the items present in cart");
        String screenshotPath = Utils.getScreenshotForCucumberExtentReport("Cart Page", Utils.getCurrentDateTimeTimestampString(), this.SDSess.baseTest.driver);
        testReportNode.addScreenCaptureFromPath( screenshotPath );

    }

    @Given("^Check if items present in cart (.+)$")
    public void check_if_items_present_in_cart( String productName ) throws IOException {
        this.cartPage.checkItemInCartByName( productName );

        ExtentTest threadSafeExtentTest = ExtentTestManager.getTest();
        ExtentTest testReportNode = threadSafeExtentTest.createNode("Cart Page");
        testReportNode.info("Checking for the item present in cart");
        testReportNode.info( "Product - " + productName );
        String screenshotPath = Utils.getScreenshotForCucumberExtentReport("Cart Page", Utils.getCurrentDateTimeTimestampString(), this.SDSess.baseTest.driver);
        testReportNode.addScreenCaptureFromPath( screenshotPath );
    }


}
