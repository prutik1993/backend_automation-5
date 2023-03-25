package stepDef.ui_step_def;


import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import ui.pages.TechGlobalFrontendTestingHomePage;
import utils.Driver;

public class TechGlobalSteps {


    WebDriver driver;
    TechGlobalFrontendTestingHomePage techGlobalFrontendTestingHomePage;


    @Before
    public void setup(){
        driver = Driver.getDriver();
        techGlobalFrontendTestingHomePage = new TechGlobalFrontendTestingHomePage();
    }

    @When("user clicks on Practices dropdown in the header")
    public void userClicksOnPracticesDropdownInTheHeader() {
        techGlobalFrontendTestingHomePage.headerDropdown.click();
    }

    @And("user selects the {string} option")
    public void userSelectsTheOption(String option) {
        switch (option) {
            case "Frontend Testing":
                techGlobalFrontendTestingHomePage.headerDropdownOptions.get(0).click();
                break;
            case "Backend Testing":
                techGlobalFrontendTestingHomePage.headerDropdownOptions.get(1).click();
                break;
            case "Dynamic Tables":
            case "Alerts":
            case "Login Form":
                techGlobalFrontendTestingHomePage.clickOnCard(option);
                break;
            default:
                throw new NotFoundException();
        }
    }




}