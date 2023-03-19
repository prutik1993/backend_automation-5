package api.tdd.petStore;

import api.pojo_classes.pet_store.AddAPet;
import api.pojo_classes.pet_store.Category;
import api.pojo_classes.pet_store.Tags;
import api.pojo_classes.pet_store.UpdateAPet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddPetToStoreWithLombok {

    static Logger logger = LogManager.getLogger(AddPetToStoreWithLombok.class);

    Response response;

    @BeforeSuite
    public void testStarts(){
        logger.info("Starting the test suite");
    }

    @BeforeTest
    public void beforeTest(){
        RestAssured.baseURI = ConfigReader.getProperty("PetStoreBaseURL");
    }

    @Test
    public void addPetToStoreCRUDWithLombok() throws JsonProcessingException {

        Category category = Category
                .builder()
                .id(10)
                .name("horse")
                .build();

        Tags tags0 = Tags
                .builder()
                .id(71)
                .name("black")
                .build();

        Tags tags1 = Tags
                .builder()
                .id(72)
                .name("white")
                .build();

        AddAPet addAPet = AddAPet
                .builder()
                .id(8)
                .category(category)
                .name("pretty")
                .photoUrls(Arrays.asList("Photo Url for this animal"))
                .tags(Arrays.asList(tags0,tags1))
                .status("available")
                .build();

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(addAPet)
                .when().post("/v2/pet")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response();


        // getting pet id from response body
        int actualPetId = response.jsonPath().getInt("id");

        // getting pet id from request body
        int expectedPetId = addAPet.getId();

        int actualTagsId = response.jsonPath().getInt("tags[0].id");
        int expectedTagsId0 = tags0.getId();

        int actualPetIdWithJayWay = JsonPath.read(response.asString(), "id");

        int actualPetTag0IdWithJayWay = JsonPath.read(response.asString(), "tags[0].id");
        logger.info("My pet tag id with jayway is " + actualPetTag0IdWithJayWay);

        int actualCategoryIdWithJayWay = JsonPath.read(response.asString(), "category.id");
        logger.info("Category id with JayWay is " + actualCategoryIdWithJayWay);

        int expectedCategoryId = category.getId();


        logger.info("My id with JayWay is " + actualPetTag0IdWithJayWay);

        // we are logging information
        logger.info("My actual pet id is " + actualPetId);

        // we are debugging assertion
        logger.debug("The actual pet id should be " + expectedPetId + " but we found " + actualPetId);
       // Assert.assertEquals(actualPetId, expectedPetId);

        // assertion with hamcrest

        assertThat(
                // reason why we are asserting
                "I am checking if the " + expectedPetId + " is matching with the " + actualPetIdWithJayWay,
                //actual value
                actualPetIdWithJayWay,
                //expected value
                is(expectedPetId)
        );

        logger.info("The actual category Id " + actualCategoryIdWithJayWay + " expected " + expectedCategoryId);
        assertThat(
                "I am validating category Id",
                actualCategoryIdWithJayWay,
                is(expectedCategoryId)
        );


        Assert.assertEquals(actualTagsId, expectedTagsId0);

        System.out.println("______Update a pet______");

        Category updateCategory = Category
                .builder()
                .id(11)
                .name("horse")
                .build();

        UpdateAPet updateAPet = UpdateAPet
                .builder()
                .id(8)
                .category(updateCategory)
                .name("pretty")
                .photoUrls(Arrays.asList("Photo Url for this animal"))
                .tags(Arrays.asList(tags0,tags1))
                .status("unavailable")
                .build();

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(updateAPet)
                .when().put("/v2/pet")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response();

    }
}
