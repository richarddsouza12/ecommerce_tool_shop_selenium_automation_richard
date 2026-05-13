package richardenterprises.page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import richardenterprises.recources.Utils;

import java.time.Duration;

public class CheckoutPage {

    // Guest Tab Customer Basic Details Email Name
    private final By byCheckoutButton = By.xpath("//button[@data-test='proceed-1']");
    private final By byContinueAsGuestTabATag = By.xpath("//a[normalize-space()='Continue as Guest']");
    private final By byGuestFirstNameInput = By.xpath("//input[@id='guest-first-name']");
    private final By byGuestLastNameInput = By.xpath("//input[@id='guest-last-name']");
    private final By byGuestEmailInput = By.xpath("//input[@id='guest-email']");
    private final By byContinueAsGuestButton = By.xpath("//input[@data-test='guest-submit']");
    private final By byContinueAsGuestButton2 = By.xpath("//button[@data-test='proceed-2-guest']");

    //Billing Address Section
    private final By byBillingAddressH3 = By.xpath("//h3[normalize-space()='Billing Address']");
    private final By byCountryDropdown = By.id("country");
    private final By byPostalCode = By.id("postal_code");
    private final By byHouseNo = By.id("house_number");
    private final By byStreet = By.id("street");
    private final By byCity = By.id("city");
    private final By byState = By.id("state");
    private final By byBillingProceedToCheckoutBtn3 = By.xpath("//button[@data-test='proceed-3']");

    // Payment Selection Page
    private final By byPaymentH3 = By.xpath("//h3[normalize-space()='Payment']");
    private final By byPaymentMethodSelect = By.id("payment-method");
    private final By byConfirmFinishBtn = By.xpath("//button[@data-test='finish']");
    private final By byPaymentSuccessMessageDiv = By.xpath("//div[@data-test='payment-success-message' and contains(text(),'Payment was successful')]");


    public WebDriver driver;
    public WebDriverWait webDriverWaitExplicit;


    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.webDriverWaitExplicit = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void fillGuestCustomerBasicDetails(String email, String firstName, String lastName) {

        // Guest Tab Customer Basic Details Email Name

        Actions actions = new Actions( this.driver );

        this.driver.findElement( byContinueAsGuestTabATag ).click();

        actions.moveToElement( this.driver.findElement( byGuestEmailInput ) ).click().sendKeys(email)
                .moveToElement( this.driver.findElement( byGuestFirstNameInput ) ).click().sendKeys(firstName)
                .moveToElement( this.driver.findElement( byGuestLastNameInput ) ).click().sendKeys(lastName)
                .build().perform();

        this.driver.findElement(byContinueAsGuestButton).click();

        // Show another continue button after clicking continue
        Utils.waitForPageReadyState(webDriverWaitExplicit);
        this.driver.findElement( byContinueAsGuestButton2 ).click();


    }


    public void fillBillingDetails(String pinCode, String houseNo, String street, String city, String state) {

        //Billing Address Section

        this.webDriverWaitExplicit.until(ExpectedConditions.textToBePresentInElementLocated( byBillingAddressH3 , "Billing Address" ));

        Select select = new Select(this.driver.findElement(byCountryDropdown));
        select.selectByValue("IN");

        Actions actions = new Actions( this.driver );
        actions.moveToElement( this.driver.findElement(byPostalCode) ).click().sendKeys(pinCode)
                .moveToElement( this.driver.findElement(byHouseNo) ).click().sendKeys(houseNo)
                .moveToElement( this.driver.findElement(byStreet) ).click().sendKeys(street)
                .moveToElement( this.driver.findElement(byCity) ).click().sendKeys(city)
                .moveToElement( this.driver.findElement(byState) ).click().sendKeys(state)
                .build().perform();

        this.webDriverWaitExplicit.until(ExpectedConditions.elementToBeClickable( byBillingProceedToCheckoutBtn3 ));
        this.driver.findElement( byBillingProceedToCheckoutBtn3 ).click();

    }

    public void selectPaymentMethodCashOnDeliveryAndCompleteIt() {


        webDriverWaitExplicit.until(ExpectedConditions.textToBePresentInElementLocated( byPaymentH3 , "Payment" ));
        Select select = new Select( this.driver.findElement(byPaymentMethodSelect) );
        select.selectByValue("cash-on-delivery");
        this.driver.findElement( byConfirmFinishBtn ).click();

        //proceed to payment and complete
        webDriverWaitExplicit.until( ExpectedConditions.textToBePresentInElementLocated( byPaymentSuccessMessageDiv , "Payment was successful" ) );

        webDriverWaitExplicit.until( ExpectedConditions.elementToBeClickable( byConfirmFinishBtn ) );
        this.driver.findElement( byConfirmFinishBtn ).click();

    }

    public String viewOrderCompletionPage() {

        By byOrderConfirmationDiv = By.xpath("//div[@id='order-confirmation']");
        webDriverWaitExplicit.until( ExpectedConditions.textToBePresentInElementLocated( byOrderConfirmationDiv, "Thanks for your order" ) );
        String orderNumber = this.driver.findElement( byOrderConfirmationDiv ).findElement( By.xpath("//span") ).getText();
        Assert.assertTrue( !orderNumber.isEmpty() );
        System.out.println( orderNumber );
        return orderNumber;
    }
}


