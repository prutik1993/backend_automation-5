package api.tdd.goRest;

import api.pojo_classes.go_rest.CreateGoRestUserWithLombok;
import api.pojo_classes.go_rest.UpdateGoRestUserWithLombok;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class GoRestWithLombokSchemaValidation {

    static Logger logger = LogManager.getLogger(GoRestWithLombokSchemaValidation.class);
    /**
     * it will convert java into json, it's a class that coming it from fasterxml
     */
    Response response;
    ObjectMapper objectMapper = new ObjectMapper();
    Faker faker = new Faker();
    int expectedGoRestID;
    String expectedGoRestName;
    String expectedGoRestEmail;
    String expectedGoRestGender;
    String expectedGoRestStatus;


    @BeforeTest
    public void beforeTest() {
        System.out.println("Starting the API test");
        // By having RestAssure URI set implicitly in to the rest assured
        // we just add path to the post call
        RestAssured.baseURI = ConfigReader.getProperty("GoRestBaseURI");
    }

    @Test
    public void goRestCRUDWithLombok() throws JsonProcessingException, FileNotFoundException {

        // creating a POJO (Bean) object
        CreateGoRestUserWithLombok createUser = CreateGoRestUserWithLombok
                .builder()
                .name("Anastasiya")
                .email(faker.internet().emailAddress())
                .gender("female")
                .status("active")
                .build();

        System.out.println("________Creating the user with POST request_______");
        response = RestAssured
                .given().log().all()
                //.header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                //.header("Authorization", "Bearer 1e77ced1b303c9427d4be4369c44d1872f098681bb2c3569412b13415d945406")
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .body(createUser)
                //.when().post( "https://gorest.co.in/public/v2/users")
                .when().post("/public/v2/users")
                .then().log().all()
                // validating status code
                .and().assertThat().statusCode(201)
                // validating that execution time less than 2 sec
               // .time(Matchers.lessThan(9000L))
                // validating the value from the body with hamcrest
                .body("name", equalTo("Anastasiya"))
                // validating response content type
                .contentType(ContentType.JSON)
                // validating schema response body with rest assured schema validation library
                .assertThat()
                .body(JsonSchemaValidator
                        .matchesJsonSchema(new FileInputStream("src/test/java/api/json_schema/go_rest/go_rest_post_response.json")))
                .extract().response();

        int goRestId = response.jsonPath().getInt("id");

        // find expected name and actual name

        // expected name with lombok
        String expectedName = createUser.getName();
        // find actual name with jayway
        String actualName = JsonPath.read(response.asString(),"name");

        // debug it with logger
        logger.debug("The name value should be " + expectedName + " but we found " + actualName);

        //assert it with Hamcrest
        assertThat(
                // reason why we are asserting
                "I am checking if the " + expectedName + " is matching with the " + actualName,
                //actual value
                actualName,
                //expected value
                is(expectedName)
        );



        System.out.println("________Fetching specific user with GET request_______");
        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .when().get("/public/v2/users/" + goRestId)
                .then().log().all()
                .and().assertThat().statusCode(200)
                // .time(Matchers.lessThan(2000L))
                .body("name", equalTo("Anastasiya"))
                .contentType(ContentType.JSON)
                .extract().response();

        System.out.println("________Patching all users with GET request_______");
        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .when().get("/public/v2/users")
                .then().log().all()
                .and().assertThat().statusCode(200)
               // .time(Matchers.lessThan(3000L))
                //.body("name", equalTo("Anastasiya"))
                .contentType(ContentType.JSON)
                .extract().response();

        System.out.println("________Updating the user with PUT request_______");

        UpdateGoRestUserWithLombok updateGoRestUserWithLombok = UpdateGoRestUserWithLombok
                // building updated body
                .builder()
                .email(faker.internet().emailAddress())
                .gender("male")
                .status("inactive")
                .build();

        response = RestAssured
                .given().log().all()
                //.header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                //.header("Authorization", "Bearer 1e77ced1b303c9427d4be4369c44d1872f098681bb2c3569412b13415d945406")
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .body(updateGoRestUserWithLombok)
                //.when().post( "https://gorest.co.in/public/v2/users")
                .when().put("/public/v2/users/" + goRestId)
                .then().log().all()
                // validating status code
                .and().assertThat().statusCode(200)
                // validating that execution time less than 2 sec
              //  .time(Matchers.lessThan(3000L))
                // validating the value from the body with hamcrest
                .body("name", equalTo("Anastasiya"))
                // validating response content type
                .contentType(ContentType.JSON)
                .extract().response();

        System.out.println("________Deleting user with DELETE request_______");
        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .when().delete("/public/v2/users/" + goRestId)
                .then().log().all()
                .and().assertThat().statusCode(204)
               // .time(Matchers.lessThan(3000L))
                .extract().response();

    }
}