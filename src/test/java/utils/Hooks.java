package utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static utils.ConfigReader.getProperty;



public class Hooks {

    private static Logger logger = LogManager.getLogger(Hooks.class);

    public static String goRestBaseUrl;
    public static String petStoreBaseUrl;
    public static String techGlobalBaseUrl;
    public static String token;

    public static Response response;

    public static List<List<Object>> queryResult;

    @Before
    public void setUp(){
        goRestBaseUrl = getProperty("GoRestBaseURI");
        petStoreBaseUrl = getProperty("PetStoreBaseURL");
        techGlobalBaseUrl = getProperty("TechGlobalBaseURL");
        token = getProperty("GoRestToken");
    }

    @After
    public void teardown(){
        logger.info("Ending the test");
    }


}
