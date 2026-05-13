package richardenterprises.recources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    private static Utils utils;
    private WebDriver driver;

    private Utils( WebDriver driver ) {
        this.driver = driver;
    }

    public static Utils getOrCreateUtilsInstance( WebDriver driver ) {
        if( utils == null ) {
            utils = new Utils( driver );
        }
        return utils;
    }

    public void removeWebElementIfPresent( WebElement webElement ) {

        if( webElement != null ) {
            //send js to remove it.
            JavascriptExecutor jse_jse = ( JavascriptExecutor ) this.driver;
            String javascript = "arguments[0].remove();";
            jse_jse.executeScript( javascript, webElement );

        }
    }


    public void scrollToElement( WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) this.driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void executeJavascript( WebElement element, String script) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) this.driver;
        jsExecutor.executeScript( script, element);
    }

    public boolean findStringInAllWindowHandles( String targetString ) {

        // Get all window handles
        boolean found = false;

        int i = 1;
        String main_window_handle = "";
        for (String windowHandle : driver.getWindowHandles()) {
            if( i==1) {
                main_window_handle = windowHandle;
            }
            // Switch to the window
            driver.switchTo().window(windowHandle);
            // Get the current window's URL
            String currentURL = driver.getCurrentUrl();
            // Check if the URL contains the target string
            if (currentURL.contains(targetString)) {
                found = true;
                break;
            }
            i++;
        }
        driver.switchTo().window(main_window_handle);
        return found;
    }

    public static void waitForPageReadyState(WebDriverWait webDriverWaitExplicit) {
        webDriverWaitExplicit.until(webDriver ->
                ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );
    }

    public static String getPrettyStringFromAnyDataObject(ObjectMapper mapper, Object anyDataObject ) throws JsonProcessingException {
        String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(anyDataObject);
        return prettyJson;
    }

    public static String getScreenshot( String screenshotFileName, String dateTimeStamp,  WebDriver driver ) throws IOException {

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File source = takesScreenshot.getScreenshotAs( OutputType.FILE );
        String destinationFilePath = System.getProperty("user.dir") + "/extent-reports/screenshots/" + dateTimeStamp + "_" + screenshotFileName + ".png";
        File destFile = new File(destinationFilePath);
        FileUtils.copyFile(source, destFile);
        return destinationFilePath;

    }

    public static String getScreenshotForCucumberExtentReport( String screenshotFileName, String dateTimeStamp,  WebDriver driver ) throws IOException {

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File source = takesScreenshot.getScreenshotAs( OutputType.FILE );
        String destinationFilePath = System.getProperty("user.dir") + "/cucumber_reports/custom-extent-reports/screenshots/" + dateTimeStamp + "_" + screenshotFileName + ".png";
        File destFile = new File(destinationFilePath);
        FileUtils.copyFile(source, destFile);
        return destinationFilePath;

    }

    public static String getCurrentDateTimeTimestampString(  )  {

        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(
                        "dd-MM-yyyy-HH-mm-ss"
                ));
    }




}
