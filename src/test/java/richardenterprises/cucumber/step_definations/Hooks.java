package richardenterprises.cucumber.step_definations;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.cucumber.java.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.BeforeMethod;
import richardenterprises.cucumber.StepDefinationContextSession;
import richardenterprises.recources.ExtentReporterterNg;
import richardenterprises.recources.ExtentTestManager;
import richardenterprises.recources.Utils;


import java.io.File;
import java.io.IOException;

public class Hooks {

    public StepDefinationContextSession SDSess;

    public static ExtentReports extentReports;

    public Hooks(StepDefinationContextSession stepDefinationContextSession ) {
        this.SDSess = stepDefinationContextSession;
    }

    /*
      -------------------------------------------------
      BEFORE SUITE - Runs once before all scenarios
      -------------------------------------------------
   */
    @BeforeAll
    public static void beforeSuite() {

        extentReports = ExtentReporterterNg.getCreateExtentReportObjectForCucumber( Utils.getCurrentDateTimeTimestampString() );
        System.out.println("===== BEFORE SUITE =====");
    }


    /*
   -------------------------------------------------
   BEFORE SCENARIO - Runs before every scenario
   -------------------------------------------------
   */
    @Before
    public void beforeScenario(Scenario scenario) {


        System.out.println("===== BEFORE SCENARIO =====");
        System.out.println("Scenario Name: " + scenario.getName());

        //create a Test for main Extent Report.
        ExtentTest test = extentReports.createTest(scenario.getName());
        ExtentTestManager.setTest(test);
    }

    /*
     -------------------------------------------------
     After SCENARIO - Runs before every scenario
     -------------------------------------------------
    */
    @After
    public void after(Scenario scenario) {

        if (scenario.isFailed()) {
            ExtentTestManager.getTest().log( Status.FAIL,"Test Failed");
        } else {
            ExtentTestManager.getTest().log( Status.PASS,"Test Passed");
        }

        SDSess.baseTest.driver.quit();
    }

    @AfterStep
    public void afterStep(Scenario scenario ) throws IOException {
        if( scenario.isFailed() ) {

            //Attach to cucumber report
            File sourcePath = ( (TakesScreenshot) SDSess.baseTest.driver ).getScreenshotAs(OutputType.FILE);
            byte[] fileContent = FileUtils.readFileToByteArray(sourcePath);
            scenario.attach( fileContent, "image/png","image");

            //attach to custom extent report
        }

    }

    /*
    --------------------------------------------
    AFTER SUITE - Runs once after all scenarios
    --------------------------------------------
    */
    @AfterAll
    public static void afterSuite() {

        System.out.println("===== AFTER SUITE =====");
        extentReports.flush();

    }
}