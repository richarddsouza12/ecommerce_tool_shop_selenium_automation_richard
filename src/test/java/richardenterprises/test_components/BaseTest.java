package richardenterprises.test_components;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    public WebDriver driver;

    public static ThreadLocal<WebDriver> tl_webdriver = new ThreadLocal<WebDriver>();

    public void initializeWebDriver() throws IOException {

        Properties properties = new Properties();
        //FileInputStream is = new FileInputStream("../../main/java/selenium_framework_design_richard/recources/GolbalData.properties");
        //or

        FileInputStream fileInputStream = new FileInputStream(
                System.getProperty("user.dir") + "/src/main/java/richardenterprises/recources/GolbalData.properties");
        properties.load( fileInputStream );

        boolean boolIsHeadlessMode = ( System.getProperty("isHeadless") != null && System.getProperty("isHeadless").equals("true") );

                        /* Obtained form terminal */
        String browser_name = ( System.getProperty("browser") != null ) ? System.getProperty("browser") : properties.getProperty("browser");

        if( browser_name.equals("chrome") ) {

            /* not needed since selenium v4 onwards */
           /* WebDriverManager.chromedriver().setup();*/

            if( boolIsHeadlessMode ) {

                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("headless");
                this.driver = new ChromeDriver( chromeOptions );
                this.driver.manage().window().setSize( new Dimension( 1440,900 ) );

            } else {
                ChromeOptions chromeOptions = new ChromeOptions();
//                chromeOptions.addExtensions(
//                        new File("/home/richard/Downloads/Softwares/DevelopmentSoftwares/Chrome/SeleniumSelectorsHub/selector_hub_5_1_7_0.crx")
//                );
                this.driver = new ChromeDriver( chromeOptions );
            }


        }
        else if ( browser_name.equals("firefox") ) {

            /* not needed since selenium v4 onwards */
            /* WebDriverManager.chromedriver().setup();*/
            this.driver = new FirefoxDriver();
        }


        if(  properties.getProperty("run_in_maximised_mode").equals("true") ) {
            this.driver.manage().window().maximize();
        }

        //global implict wait to manage page load elment poling.
        this.driver.manage().timeouts().implicitlyWait(Integer.parseInt( properties.getProperty("implicit_wait_duration_seconds")), TimeUnit.SECONDS );

        //set global accessable varriable for driver
        tl_webdriver.set(  this.driver );

    }

    public static WebDriver getThreadLocalWebDriverObject( ) {
        return tl_webdriver.get();
    }

}
