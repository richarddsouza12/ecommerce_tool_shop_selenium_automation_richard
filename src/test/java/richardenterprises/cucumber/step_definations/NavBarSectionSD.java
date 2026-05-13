package richardenterprises.cucumber.step_definations;

import io.cucumber.java.en.Given;
import richardenterprises.cucumber.StepDefinationContextSession;
import richardenterprises.page_objects.NavBarSectionPage;

public class NavBarSectionSD {

    public StepDefinationContextSession SDSess;

    public NavBarSectionSD( StepDefinationContextSession stepDefinationContextSession ) {

        this.SDSess = stepDefinationContextSession;
    }

    @Given("Click cart icon")
    public void click_cart_icon() {

        //Nav Section
        NavBarSectionPage navBarSectionPage = new NavBarSectionPage( this.SDSess.baseTest.driver );
        navBarSectionPage.clickCartIcon();

    }

}
