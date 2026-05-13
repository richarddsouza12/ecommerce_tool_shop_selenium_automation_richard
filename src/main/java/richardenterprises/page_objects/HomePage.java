package richardenterprises.page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {

    public WebDriver driver;
    public WebDriverWait webDriverWaitExplicit;
    public By byProductCards = By.xpath("//a[@class='card']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.webDriverWaitExplicit = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void selectFirstProduct() {

        //Main Page
        List<WebElement> productCardWebElementsArray =  this.driver.findElements(this.byProductCards);
        WebElement firtsProductCardWebElement = productCardWebElementsArray.get(0);
        firtsProductCardWebElement.click();

    }

    public void selectProductByProductId( String productId ) {

        By byProductById = By.xpath( String.format("//a[@data-test='product-%s']", productId));
//        By byProductById = By.xpath( "//a[@data-test='product-"+ productId +"']");
        WebElement productWebElementATag =  this.driver.findElement(byProductById);
        productWebElementATag.click();

    }


    public void selectProductByProductName( String productName ) {

        By byProductCardATag = By.xpath( String.format("//a[@class='card']//div//*[@data-test='product-name' and contains(text(),'%s')]//ancestor::a[contains(@class,'card')]", productName));
        WebElement productWebElementATag =  this.driver.findElement(byProductCardATag);
        productWebElementATag.click();

    }

}