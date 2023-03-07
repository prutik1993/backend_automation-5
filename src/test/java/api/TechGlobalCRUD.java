package api;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TechGlobalCRUD {
    public static void main(String[] args) {

        Response response;
        Faker faker = new Faker();

        System.out.println("________Creating post request________");
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"firstName\": \"" + faker.name().firstName() + "\",\n" +
                        "    \"lastName\": \"" + faker.name().lastName() + "\",\n" +
                        "    \"email\": \"" + faker.internet().emailAddress() + "\",\n" +
                        "    \"dob\": \"1997-01-01\"\n" +
                        "}")
                .when().post("https://tech-global-training.com/students")
                .then().log().all().extract().response();


        int postId = response.jsonPath().getInt("id");

         System.out.println("________Creating get request to get specific students________");
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .when().get("https://tech-global-training.com/students/" + postId)
                .then().log().all().extract().response();


        System.out.println("________Creating get request to get all students________");
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .when().get("https://tech-global-training.com/students")
                .then().log().all().extract().response();

        System.out.println("________Updating specific user________");
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"firstName\": \"" + faker.name().firstName() + "\",\n" +
                        "    \"lastName\": \"" + faker.name().lastName() + "\",\n" +
                        "    \"email\": \"" + faker.internet().emailAddress() + "\",\n" +
                        "    \"dob\": \"1997-01-01\"\n" +
                        "}")
                .when().put("https://tech-global-training.com/students/" + postId)
                .then().log().all().extract().response();

        System.out.println("________Updating specific user with patch________");
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"lastName\": \"" + faker.name().lastName() + "\",\n" +
                        "    \"email\": \"" + faker.internet().emailAddress() + "\"\n" +
                        "}")
                .when().patch("https://tech-global-training.com/students/" + postId)
                .then().log().all().extract().response();


        System.out.println("________Deleting specific user________");
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .when().delete("https://tech-global-training.com/students/" + postId)
                .then().log().all().extract().response();

        System.out.println("________Deleting all users________");
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .when().delete("https://tech-global-training.com/students")
                .then().log().all().extract().response();



    }
}
