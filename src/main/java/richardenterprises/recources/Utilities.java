package richardenterprises.recources;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class Utilities {

    public String getScreenshot( String testCaseName, String dateTimeStamp,  WebDriver driver ) throws IOException {

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File source = takesScreenshot.getScreenshotAs( OutputType.FILE );
        String destinationFilePath = System.getProperty("user.dir") + "/extent-reports/screenshots/" + dateTimeStamp + "/" + testCaseName + ".png";
        File destFile = new File(destinationFilePath);
        FileUtils.copyFile(source, destFile);
        return destinationFilePath;

    }

}
