package richardenterprises.recources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterterNg {

    public static ExtentReports getCreateExtentReportObject(String dateTimeStamp ) {

        String path = System.getProperty("user.dir") +"/extent-reports/" + dateTimeStamp +"/index.html";
        ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter( path );
        extentSparkReporter.config().setReportName("Web Automation Results");
        extentSparkReporter.config().setDocumentTitle("Test Results");

        ExtentReports extentReports = new ExtentReports();
        extentReports.attachReporter( extentSparkReporter );
        extentReports.setSystemInfo("Tester" , "Richard Dsouza");
        return extentReports;

    }

    public static ExtentReports getCreateExtentReportObjectForCucumber(String dateTimeStamp ) {

        String path = System.getProperty("user.dir") +"/cucumber_reports/custom-extent-reports/" + dateTimeStamp +"/index.html";
        ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter( path );
        extentSparkReporter.config().setReportName("Web Automation Results");
        extentSparkReporter.config().setDocumentTitle("Test Results");

        ExtentReports extentReports = new ExtentReports();
        extentReports.attachReporter( extentSparkReporter );
        extentReports.setSystemInfo("Tester" , "Richard Dsouza");
        return extentReports;

    }

}
