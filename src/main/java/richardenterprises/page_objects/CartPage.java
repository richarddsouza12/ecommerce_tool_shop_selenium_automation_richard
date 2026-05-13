package richardenterprises.page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class CartPage {

    public WebDriver driver;
    public WebDriverWait webDriverWaitExplicit;
    public By byCartTable = By.xpath("//app-cart//table");
    public By byCheckoutButton = By.xpath("//button[@data-test='proceed-1']");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.webDriverWaitExplicit = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void checkItemInCartByName( String productName ){

        this.waitForCartItemsToLoad();
        WebElement wbelmSpanProductName = this.driver.findElement(byCartTable).findElement(By.xpath( String.format( "//td//span[contains(text(),'%s')]",productName.trim() ) ));
        Assert.assertEquals( wbelmSpanProductName.getText().trim() , productName );
    }

    public void checkIfCartNotEmpty(){

        this.waitForCartItemsToLoad();
        int size = this.driver.findElement(byCartTable).findElements(By.xpath("//tbody//tr")).size();
        Assert.assertTrue( size > 0 );

    }

    public void waitForCartItemsToLoad(){

        webDriverWaitExplicit.until(ExpectedConditions.visibilityOfElementLocated( By.xpath("//app-cart//table//tbody//tr") ));

    }


    public void clickProceedToCheckout() {
        this.driver.findElement( byCheckoutButton ).click();
    }
}