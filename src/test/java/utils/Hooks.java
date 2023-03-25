package utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static utils.ConfigReader.getProperty;



public class Hooks {

    private static Logger logger = LogManager.getLogger(Hooks.class);

    public static String goRestBaseUrl;
    public static String petStoreBaseUrl;
    public static String techGlobalBaseUrl;
    public static String token;

    public static Response response;

    public static List<WebElement> studentList = new ArrayList<>();
    public static List<List<Object>> newList = new ArrayList<>();

    public static List<Object> firstAndLastNames = new ArrayList<>();

    public static List<List<Object>> queryResult;

    @Before
    public void setUp(){
        goRestBaseUrl = getProperty("GoRestBaseURI");
        petStoreBaseUrl = getProperty("PetStoreBaseURL");
        techGlobalBaseUrl = getProperty("TechGlobalBaseURL");
        token = getProperty("GoRestToken");
    }

    @After
    public void teardownTest(Scenario scenario) {
        System.out.println("Scenario = " + scenario.getName() + "\nStatus = " + scenario.getStatus());
        try {
            if (scenario.isFailed()) {
                byte[] screenshot = ((TakesScreenshot) Driver.getDriver())
                        .getScreenshotAs(OutputType.BYTES);

                scenario.attach(screenshot, "image/png", "Taking the screenshot");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Driver.quitDriver();
        }
    }
}
