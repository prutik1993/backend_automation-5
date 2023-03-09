package api.tdd.petStore;

import api.pojo_classes.pet_store.AddAPet;
import api.pojo_classes.pet_store.Category;
import api.pojo_classes.pet_store.Tags;
import api.pojo_classes.pet_store.UpdateAPet;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.util.Arrays;

public class AddPetToStoreWithLombok {

    Response response;

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

        Assert.assertEquals(actualPetId, expectedPetId);
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
