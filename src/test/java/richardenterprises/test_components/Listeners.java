package richardenterprises.test_components;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import richardenterprises.recources.ExtentReporterterNg;
import richardenterprises.recources.ExtentTestManager;
import richardenterprises.recources.Utilities;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Listeners implements ITestListener {

    public String currentDateTimestamp;
    public ExtentReports extentReports;
    public WebDriver webDriver;

    public Listeners() {
        this.extentReports = ExtentReporterterNg.getCreateExtentReportObject( this.getCurrentDateTimeStamp() );
    }

    private String getCurrentDateTimeStamp() {

        if( this.currentDateTimestamp == null ) {
            this.currentDateTimestamp =  new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss").format(new Date());
        }
        return this.currentDateTimestamp;
    }

    //called for each method test start.
    @Override
    public void onTestStart( ITestResult result ) {
        ITestListener.super.onTestStart(result);

        //create a Test for main Extent Report.
        ExtentTest test = extentReports.createTest(result.getMethod().getMethodName());
        ExtentTestManager.setTest(test);

    }

    //called for each
    @Override
    public void onTestSuccess(ITestResult result) {
        ITestListener.super.onTestSuccess(result);
        ExtentTestManager.getTest().log( Status.PASS,"Test passed"); //logging of entent reporter.
        this.webDriver = this.getActiveThreadWebDriverInstance( result );
        if( this.webDriver != null ) {
            //this.webDriver.quit();
        }
    }

    //called for each
    @Override
    public void onTestFailure( ITestResult result ) {
        
        ITestListener.super.onTestFailure(result);
        String screenshotPath;
        WebDriver driver = null;
        Utilities utilities = new Utilities();

        //there is no driver instace here.
        /*
        How to get Method 1 : form result object and instace stack list.
        driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());

        Method 2 : using ThreadLocal
            SomeClass{
            public static ThreadLocal<WebDriver> tl_webdriver = new ThreadLocal<WebDriver>();
            }
            //set it somewhere.
            tl_webdriver.set(driver);

            //get it in runtime from any class
            driver = SomeClass.tl_webdriver.get();
        * */

        try {
            driver = this.getActiveThreadWebDriverInstance( result );
        } catch ( Exception e) {
           e.printStackTrace();
        }

        ///address error msg in report.
        ExtentTestManager.getTest().fail(result.getThrowable());

        //take Screenshot.
        if( driver != null ) {  //added this condition due to non selenium test cases only.
            try {
                screenshotPath = utilities.getScreenshot( result.getMethod().getMethodName(), this.getCurrentDateTimeStamp() , driver );
                ExtentTestManager.getTest().addScreenCaptureFromPath( screenshotPath );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //driver.quit();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    //called at <test> </test> start
    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
        extentReports.flush();
    }

    private WebDriver getActiveThreadWebDriverInstance(ITestResult result ) {

        if( this.webDriver == null ) {
            try {
                this.webDriver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return webDriver;

    }

}
