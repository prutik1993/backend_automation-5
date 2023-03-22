package stepDef.common_step_def;

import api.pojo_classes.tech_global.StudentsMap;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static utils.Hooks.*;

public class CommonStepDef {

    private static Logger logger = LogManager.getLogger(CommonStepDef.class);

    int actualStatusCode;

    @Given("Validate that status code is {int}")
    public void validateThatStatusCodeIs(Integer expectedStatusCode) {

        logger.info("Getting the Status code from the response");

        actualStatusCode = response.statusCode();

        assertThat(
                "Validating status Code",
                actualStatusCode,
                is(expectedStatusCode)
        );
    }
    @Then("I validate data from DB with API")
    public void iValidateDataFromDBWithAPI() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        List<StudentsMap> studentsMaps = objectMapper.readValue(response.asString(), new TypeReference<List<StudentsMap>>(){});

        System.out.println("\n" + studentsMaps + "\n");

        studentsMaps.stream().forEach(x -> System.out.println(x));

        for (int i = 0; i < studentsMaps.size(); i++) {

            Assert.assertEquals(String.valueOf(studentsMaps.get(i).id), queryResult.get(i).get(0).toString());
            Assert.assertEquals(studentsMaps.get(i).firstName, queryResult.get(i).get(3));
            Assert.assertEquals(studentsMaps.get(i).lastName, queryResult.get(i).get(4));
            Assert.assertEquals(studentsMaps.get(i).email, queryResult.get(i).get(2));
            Assert.assertEquals(studentsMaps.get(i).dob, queryResult.get(i).get(1));
        }
    }
}
