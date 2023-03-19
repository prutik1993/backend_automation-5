package api.tdd.petStore;

import api.pojo_classes.pet_store.AddAPet;
import api.pojo_classes.pet_store.Category;
import api.pojo_classes.pet_store.Tags;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigReader;
import utils.DataProviderUtil;

import java.util.Arrays;

public class AddPetToStoreWithLombokDataProvider {

    static Logger logger = LogManager.getLogger(AddPetToStoreWithLombok.class);

    Response response;

    @BeforeSuite
    public void testStarts() {
        logger.info("Starting the test suite");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("Starting the API test");
        // By having RestAssured URI set implicitly in to rest assured
        // we just add path to the post call
        RestAssured.baseURI = ConfigReader.getProperty("PetStoreBaseURL");
    }

    @Test(dataProvider = "DataFromExcel", dataProviderClass = DataProviderUtil.class)
    // All the data type coming from Excel file is String, so all the method arguments are String
    public void addPetWithDataProvider(String pet_id, String category_id, String category_name, String pet_name,
                                       String pet_photoUrls, String pet_tag_id, String pet_tag_name, String pet_status){

        Category category = Category
                .builder()
                .id(Integer.parseInt(category_id))
                .name(category_name)
                .build();

        Tags tags = Tags
                .builder()
                .id(Integer.parseInt(pet_tag_id))
                .name(pet_tag_name)
                .build();

        AddAPet addAPet = AddAPet
                .builder()
                .id(Integer.parseInt(pet_id))
                .category(category)
                .name(pet_name)
                .photoUrls(Arrays.asList(pet_photoUrls))
                .tags(Arrays.asList(tags))
                .status(pet_status)
                .build();

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(addAPet)
                .when().post("/v2/pet")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response();

    }
}
