package stepDef.ui_step_def;


import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import utils.Driver;

public class BaseSteps {
    WebDriver driver;

    @Before
    public void setup(){
        driver = Driver.getDriver();
    }

    @Given("user navigates to {string}")
    public void userNavigatesTo(String url) {
        driver.get(url);
    }

    @Then("user should see {string} in the url")
    public void user_should_see_in_the_url(String key) {
        for (String word : key.split(" ")) {
            Assert.assertTrue(driver.getCurrentUrl().contains(word));
        }
//    Assert.assertTrue(driver.getCurrentUrl().trim().replaceAll(" ", "_").contains(key));
    }
    @Then("user should see {string} in the title")
    public void user_should_see_in_the_title(String key) {
        Assert.assertTrue(driver.getTitle().contains(key));
    }
}