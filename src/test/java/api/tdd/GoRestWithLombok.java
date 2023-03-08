package api.tdd;

import api.pojo_classes.go_rest.CreateGoRestUser;
import api.pojo_classes.go_rest.CreateGoRestUserWithLombok;
import api.pojo_classes.go_rest.UpdateGoRestUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigReader;

import static org.hamcrest.core.IsEqual.equalTo;

public class GoRestWithLombok {
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
    public void goRestCRUDWithLombok() throws JsonProcessingException {

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
                .time(Matchers.lessThan(2000L))
                // validating the value from the body with hamcrest
                .body("name", equalTo("Anastasiya"))
                // validating response content type
                .contentType(ContentType.JSON)
                .extract().response();



    }
}