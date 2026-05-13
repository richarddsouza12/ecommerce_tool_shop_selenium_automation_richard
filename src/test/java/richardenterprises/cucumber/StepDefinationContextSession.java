package richardenterprises.cucumber;

import org.openqa.selenium.WebDriver;
import richardenterprises.test_components.BaseTest;


import java.io.IOException;

public class StepDefinationContextSession {

    public BaseTest baseTest; /* will always contain the fully setup/configured web driver  */

    public StepDefinationContextSession() throws IOException {
        this.configureWebDriver();
    }

    public void configureWebDriver() throws IOException {
        this.baseTest = new BaseTest();
        this.baseTest.initializeWebDriver();
    }


}
