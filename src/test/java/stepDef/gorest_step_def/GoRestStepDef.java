package stepDef.gorest_step_def;

import api.pojo_classes.go_rest.CreateGoRestUserWithLombok;
import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stepDef.api_step_def.tech_global.TechGlobalStepDef;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static utils.Hooks.*;

public class GoRestStepDef {

    private static Logger logger = LogManager.getLogger(TechGlobalStepDef.class);

    Faker faker = new Faker();

    int actualStatusCode;
    int actualId;


    @Given("Create a GoRest user with {string}, {string}, email, {string} and {string}")
    public void createAGoRstUserWithEmailAnd(String expectedName, String expectedGender, String expectedStatus, String expectedUrlPath) {

        CreateGoRestUserWithLombok createGoRestUserWithLombok = CreateGoRestUserWithLombok
                .builder()
                .name(expectedName)
                .gender(expectedGender)
                .email(faker.internet().emailAddress())
                .status(expectedStatus)
                .build();

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(createGoRestUserWithLombok)
                .when().post(goRestBaseUrl + expectedUrlPath)
                .then().log().all()
                .extract().response();

    }
    @Then("Status code {int}")
    public void statusCode(int expectedStatusCode) {

        logger.info("Getting the Status code from the response");

        actualStatusCode = response.statusCode();

        assertThat(
                "Validating status Code",
                actualStatusCode,
                is(expectedStatusCode)
        );
    }

    @And("I perform a GET request with {string} with id")
    public void iPerformAGETRequestWithWithId(String expectedUrlPath) {

        actualId = response.jsonPath().getInt("id");

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when().get(goRestBaseUrl + expectedUrlPath + "/" + actualId)
                .then().log().all()
                .extract().response();

    }

    @And("I perform PUT request with following data with {string}")
    public void iPerformPUTRequestWithFollowingData(String expectedUrlPath, Map<String,String> data) {

        CreateGoRestUserWithLombok createGoRestUserWithLombok = CreateGoRestUserWithLombok
                .builder()
                .name(data.get("name"))
                .gender(data.get("gender"))
                .email(response.jsonPath().getString("email"))
                .status(data.get("status"))
                .build();

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(createGoRestUserWithLombok)
                .when().put(goRestBaseUrl + expectedUrlPath + "/" + actualId)
                .then().log().all()
                .extract().response();
    }

    @And("I perform PATCH request with following data with {string}")
    public void iPerformPATCHRequestWithFollowingData(String expectedUrlPath, Map<String,String> data) {

        CreateGoRestUserWithLombok createGoRestUserWithLombok = CreateGoRestUserWithLombok
                .builder()
                .name(data.get("name"))
                .gender(data.get("gender"))
                .build();

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(createGoRestUserWithLombok)
                .when().patch(goRestBaseUrl + expectedUrlPath + "/" + actualId)
                .then().log().all()
                .extract().response();

    }
}
