package richardenterprises.cucumber.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;


//tags = "@special",
@CucumberOptions(features = "src/test/java/richardenterprises/cucumber/features",
        glue = "richardenterprises.cucumber.step_definations",
        monochrome = true,
        plugin = { "html:cucumber_reports/default_cucumber_report/cucumber.html",
                    "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" })
public class OrderToolsTestRunner extends AbstractTestNGCucumberTests {

    /* SCENARIO LEVEL PARALLELISM */
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
    /*
    * Make changes in pom XML file too for the parallel execution to work
    * <build>
         <pluginManagement>
                <plugins>
    *               <parallel>methods</parallel>
                    <threadCount>2</threadCount>
    * */


}
