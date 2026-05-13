package richardenterprises.page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class NavBarSectionPage {

    public WebDriver driver;
    public WebDriverWait webDriverWaitExplicit;
    public  By byNavCartIcon = By.xpath("//li[@role='menuitem']//a[@data-test='nav-cart']");

    public NavBarSectionPage(WebDriver driver) {
        this.driver = driver;
        this.webDriverWaitExplicit = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickCartIcon() {

        this.driver.findElement( this.byNavCartIcon ).click();
    }
}