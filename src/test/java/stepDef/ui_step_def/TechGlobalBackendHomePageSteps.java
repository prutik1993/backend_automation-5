package stepDef.ui_step_def;

import com.github.javafaker.Faker;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import ui.pages.TechGlobalBackendTestingHomePage;
import utils.DBUtil;
import utils.Driver;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static utils.Hooks.*;

public class TechGlobalBackendHomePageSteps {

    private static Logger logger = LogManager.getLogger(TechGlobalBackendHomePageSteps.class);

    WebDriver driver;
    TechGlobalBackendTestingHomePage techGlobalBackendTestingHomePage;
    Faker faker = new Faker();

    String email = faker.internet().emailAddress();


    @Before
    public void setup() {
        driver = Driver.getDriver();
        techGlobalBackendTestingHomePage = new TechGlobalBackendTestingHomePage();
    }

    @And("user enters {string}, {string}, email and {string} on backend page")
    public void userEntersEmailAndOnBackendPage(String firstName, String lastName, String dob) {

        techGlobalBackendTestingHomePage.firstName.sendKeys(firstName);
        techGlobalBackendTestingHomePage.lastName.sendKeys(lastName);
        techGlobalBackendTestingHomePage.backendEmail.sendKeys(email);
        techGlobalBackendTestingHomePage.dob.sendKeys(dob);

    }

    @When("user click on the add button")
    public void userClickOnTheAddButton() {
        techGlobalBackendTestingHomePage.add.click();
    }

    @And("page displays {string}")
    public void pageDisplays(String message) {
        Assert.assertEquals(techGlobalBackendTestingHomePage.successMessage.getText(), message);
    }

    @And("user can see all students on the page")
    public void userCanSeeWholeStudentsOnThePage() {

        // storing all the web elements( together all of it -> name/lastName/email/dob)
        studentList = techGlobalBackendTestingHomePage.table;

        // creating list to store each student

        for (int i = 0; i < studentList.size(); i++) {
            // adding each student to new list of objects, we splitting it in order to get information about each student separately
            newList.add(List.of(studentList.get(i).getText().trim().split(" / ")));
        }
        // printing each student
        newList.forEach(System.out::println);
    }

    @Then("the system displays user's {string}, {string}, email and {string} on backend page")
    public void theSystemDisplaysUserSEmailAndOnBackendPage(String expectedFirstName,
                                                            String expectedLastName,
                                                            String expectedDOB) throws ParseException {
        int i;
        for (i = 0; i < newList.size(); i++) {

            firstAndLastNames = List.of(newList.get(i).get(0).toString().trim().split(" "));

            if (newList.get(i).get(1).equals(email)) {
                break;
            }
        }
        // removing replacing - with / to make the date similar with coming from API or DB
        expectedDOB = expectedDOB.trim().replaceAll("/", "-");

        // Formatting the date from input to backend
        DateFormat inputFormatter = new SimpleDateFormat("MM-dd-yyyy");
        Date date = (Date) inputFormatter.parse(expectedDOB);
        DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        expectedDOB = outputFormatter.format(date);

        Assert.assertEquals(firstAndLastNames.get(0), expectedFirstName);
        Assert.assertEquals(firstAndLastNames.get(1), expectedLastName);
        Assert.assertEquals(newList.get(i).get(1), email);
        Assert.assertEquals(newList.get(i).get(2), expectedDOB);

        System.out.println("Email added when creating a student " + email);
        System.out.println("Email student in the student list " + newList.get(i).get(1));

    }
    @Then("i validate matching student was added to the database with the {string}")
    public void iValidateMatchingStudentWasAddedToTheDatabaseWithThe(String email, Map<String, String> data) {

        DBUtil.createDBConnection();
        email = response.jsonPath().getString("email");

        DBUtil.executeQuery(data.get("query") + email);

    }


}
