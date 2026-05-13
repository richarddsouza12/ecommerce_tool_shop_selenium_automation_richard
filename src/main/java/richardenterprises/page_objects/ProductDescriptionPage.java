package richardenterprises.page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ProductDescriptionPage {

    public WebDriver driver;
    public WebDriverWait webDriverWaitExplicit;
    By byAddToCartButton = By.id("btn-add-to-cart");
    By byTopRightToast = By.xpath("//div[@toast-component]");


    public ProductDescriptionPage(WebDriver driver) {
        this.driver = driver;
        this.webDriverWaitExplicit = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void addDisplayedProductToCart() {

        this.driver.findElement( this.byAddToCartButton ).click();

        this.webDriverWaitExplicit.until(ExpectedConditions.visibilityOfElementLocated(byTopRightToast));
        //Add proper assertions :

        //WebElement topRightToastWbElm = this.driver.findElement(byTopRightToast);

    }
}