package richardenterprises.cucumber.step_definations;

import com.aventstack.extentreports.ExtentTest;
import io.cucumber.java.en.Given;
import org.openqa.selenium.support.ui.WebDriverWait;
import richardenterprises.cucumber.StepDefinationContextSession;
import richardenterprises.page_objects.HomePage;
import richardenterprises.page_objects.NavBarSectionPage;
import richardenterprises.recources.ConfigManager;
import richardenterprises.recources.ExtentTestManager;
import richardenterprises.recources.Utils;

import java.time.Duration;

public class HomePageSD {

    public ConfigManager configManager;
    public WebDriverWait webDriverWaitExplicit;
    public StepDefinationContextSession SDSess;
    public HomePage homePage;

    public HomePageSD( StepDefinationContextSession stepDefinationContextSession ) {

        this.SDSess = stepDefinationContextSession;

        this.configManager = ConfigManager.createOrGetInstance();

        //implicit wait set for 5 seconds.
        this.webDriverWaitExplicit = new WebDriverWait( this.SDSess.baseTest.driver, Duration.ofSeconds(10));

        this.homePage = new HomePage( this.SDSess.baseTest.driver );

    }


    @Given("Goto products home page")
    public void goto_products_home_page() {

        this.SDSess.baseTest.driver.get( configManager.getBaseUrl() );

        //Reporting
        ExtentTest threadSafeExtentTest = ExtentTestManager.getTest();
        threadSafeExtentTest.info("Browser Launched");
        threadSafeExtentTest.info("url : " + configManager.getBaseUrl() );

    }

    @Given("Select the first displayed product")
    public void select_the_first_displayed_product() {


        //Home Page ===============================
        this.homePage.selectFirstProduct();

        //reporting
        ExtentTest threadSafeExtentTest = ExtentTestManager.getTest();
        ExtentTest testReportNode = threadSafeExtentTest.createNode("Products Home Page");
        testReportNode.info("Selected First Product");


    }

    @Given("^Select displayed product by name (.+)$")
    public void select_displayed_product_by_name( String productName ) {

        this.homePage.selectProductByProductName( productName );

        //reporting
        ExtentTest threadSafeExtentTest = ExtentTestManager.getTest();
        ExtentTest testReportNode = threadSafeExtentTest.createNode("Products Home Page");
        testReportNode.info("Selected a product");
        testReportNode.info( "Product :" + productName );

    }



}
