package stepDef.api_step_def.tech_global;

import api.pojo_classes.tech_global.Students;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import static utils.Hooks.techGlobalBaseUrl;

public class techGlobalJenkinsProject {

    Response response;
    static Logger logger = LogManager.getLogger(techGlobalJenkinsProject.class);
    Faker faker = new Faker();

    @Test
    public void goRestPOSTWithCorrectData(){

        Students students = Students
                        .builder()
                        .firstName("Elizabeth")
                        .lastName("Homenick")
                        .email(faker.internet().emailAddress())
                        .dob("1992-12-15")
                        .build();

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(students)
                .when().post( "https://tech-global-training.com/students")
                .then().log().all()
                .and().assertThat().statusCode(200)
                .contentType(ContentType.JSON)
                .extract().response();
    }
}
