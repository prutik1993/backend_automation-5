package api.tdd.goRest;

import api.pojo_classes.go_rest.CreateGoRestUser;
import api.pojo_classes.go_rest.UpdateGoRestUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigReader;
import org.hamcrest.Matchers;

import static org.hamcrest.core.IsEqual.equalTo;

public class GoRest {
    /**
     * it will convert java into json, it's a class that coming it from fasterxml(Jackson)
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
    public void goRestCRUD() throws JsonProcessingException {

        // creating a POJO (bean) object
        CreateGoRestUser createGoRestUser = new CreateGoRestUser();

        // assigned values into attributes
        createGoRestUser.setName("Anastasiya");
        createGoRestUser.setGender("female");
        createGoRestUser.setEmail(faker.internet().emailAddress());
        createGoRestUser.setStatus("active");

        System.out.println("________Creating the user with POST request_______");
        response = RestAssured
                .given().log().all()
                //.header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                //.header("Authorization", "Bearer 1e77ced1b303c9427d4be4369c44d1872f098681bb2c3569412b13415d945406")
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .body(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(createGoRestUser))
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

        System.out.println("________Fetching the user with GET request_______");

        expectedGoRestID = response.jsonPath().getInt("id");

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .when().get("/public/v2/users/" + expectedGoRestID)
                .then().log().all()
                .and().assertThat().statusCode(200)
                // validating that execution time less than 2 sec
                .time(Matchers.lessThan(2000L))
                // validating the value from the body with hamcrest
                .body("name", equalTo("Anastasiya"))
                // validating response content type
                .contentType(ContentType.JSON)
                .extract().response();

        System.out.println("________Updating the user with PUT request_______");

        //createGoRestUser.setName("Andrii");

        UpdateGoRestUser updateGoRestUser = new UpdateGoRestUser();

        updateGoRestUser.setName("Marina");
        updateGoRestUser.setEmail(faker.internet().emailAddress());

        response = RestAssured
                .given().log().all()
                //.header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                //.header("Authorization", "Bearer 1e77ced1b303c9427d4be4369c44d1872f098681bb2c3569412b13415d945406")
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .body(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(updateGoRestUser))
                //.when().post( "https://gorest.co.in/public/v2/users")
                .when().put("/public/v2/users/" + expectedGoRestID)
                .then().log().all()
                // validating status code
                .and().assertThat().statusCode(200)
                // validating that execution time less than 2 sec
                .time(Matchers.lessThan(2000L))
                // validating the value from the body with hamcrest
                .body("name", equalTo("Marina"))
                // validating response content type
                .contentType(ContentType.JSON)
                .extract().response();

        expectedGoRestName = updateGoRestUser.getName();
        expectedGoRestEmail = updateGoRestUser.getEmail();
        expectedGoRestGender = createGoRestUser.getGender();
        expectedGoRestStatus = createGoRestUser.getStatus();

        // id in the grtInt is the name of the attribute in the response body
        int actualGoRestId = response.jsonPath().getInt("id");
        String actualGoRestName = response.jsonPath().getString("name");
        String actualGoRestEmail = response.jsonPath().getString("email");
        String actualGoRestGender = response.jsonPath().getString("gender");
        String actualGoRestStatus = response.jsonPath().getString("status");

        Assert.assertEquals(actualGoRestId, expectedGoRestID);
        Assert.assertEquals(actualGoRestEmail, expectedGoRestEmail);
        Assert.assertEquals(actualGoRestGender, expectedGoRestGender);
        Assert.assertEquals(actualGoRestName, expectedGoRestName);
        Assert.assertEquals(actualGoRestStatus, expectedGoRestStatus);


        System.out.println("________Deleting the user with DELETE request_______");

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .when().delete("/public/v2/users/" + expectedGoRestID)
                .then().log().all()
                .and().assertThat().statusCode(204)
                // validating that execution time less than 2 sec
                .time(Matchers.lessThan(2000L))
                .extract().response();
    }
}