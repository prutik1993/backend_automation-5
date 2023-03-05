package api;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

public class APIAutomationSample {
    public static void main(String[] args) {

        /**
         * response is the interface coming from RestAssure library
         * The Response variable stores all the components of the API calls
         * including request and response
         * RestAssure is written with BDD flow.
         */
        Response response;

        Faker faker = new Faker();


        // creating post request
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 1e77ced1b303c9427d4be4369c44d1872f098681bb2c3569412b13415d945406")
                .body("{\n" +
                        "    \"name\": \"" + faker.name().fullName() +"\",\n" +
                        "    \"email\": \"" + faker.internet().emailAddress() + "\",\n" +
                        "    \"gender\": \"female\",\n" +
                        "    \"status\": \"active\"\n" +
                        "}")
                .when().post("https://gorest.co.in/public/v2/users")
                .then().log().all().extract().response();

        //System.out.println(response.asString());

        int postId = response.jsonPath().getInt("id");

        // creating get request ro get specific user
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 1e77ced1b303c9427d4be4369c44d1872f098681bb2c3569412b13415d945406")
                .when().get("https://gorest.co.in/public/v2/users/" + postId)
                .then().log().all().extract().response();

        //  getting all the users
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 1e77ced1b303c9427d4be4369c44d1872f098681bb2c3569412b13415d945406")
                .when().get("https://gorest.co.in/public/v2/users")
                .then().log().all().extract().response();

        // updating specific user
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization","Bearer 1e77ced1b303c9427d4be4369c44d1872f098681bb2c3569412b13415d945406")
                .body("{\n" +
                        "    \"email\": \"" + faker.internet().emailAddress() + "\",\n" +
                        "    \"status\": \"active\"\n" +
                        "}")
                .when().put("https://gorest.co.in/public/v2/users/" + postId)
                .then().log().all().extract().response();

        int patchId = response.jsonPath().getInt("id");

        Assert.assertEquals(postId, patchId, "Expected id " + postId + " we found " + patchId);

        // deleting specific user
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 1e77ced1b303c9427d4be4369c44d1872f098681bb2c3569412b13415d945406")
                .when().delete("https://gorest.co.in/public/v2/users/" + postId)
                .then().log().all().extract().response();



    }
}
