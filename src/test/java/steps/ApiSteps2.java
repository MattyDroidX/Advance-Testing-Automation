package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ApiSteps2 {

    private static RequestSpecification request;
    private Response response;
    private ValidatableResponse json;

    @Given("^I send a GET request to the (.+) URI")
    public void sendGETRequest(String URI){
        request = given().baseUri(URI)
                .contentType(ContentType.JSON);
    }
//    @Then("^I get a (\\d+) status code$")
//    public void expectedStatusCode(int expectedStatusCode){
//        response = request.when().get("users/MattyDroidX/repos");
//        json = response.then().statusCode(expectedStatusCode);
//    }
//
//    @Then("^I validate there are (\\d+) items on the (.+) endpoint$")
//    public void validateSize(int expectedSize, String endpoint){
//        response = request.when().get(endpoint);
//        List<String> jsonResponse = response.jsonPath().getList("$");
//        int actualSize = jsonResponse.size();
//        assertEquals(expectedSize,actualSize);
//    }

    @Then("^I validate there is a value: (.+) in the response at (.+) endpoints$")
    public void validateValue(String expectedValue, String endpoint){
        response = request.when().get(endpoint);
        List<String> jsonResponse = response.jsonPath().getList("username");
//        String actualValue = jsonResponse.get(0);
//        assertEquals(expectedValue,actualValue);
        assertTrue("The value " + expectedValue + " is not on the list.",jsonResponse.contains(expectedValue));
    }

    @Then("^I validate the nested value: (.+) on the response at (.+) endpoints$")
    public void validateNestedValue(String expectedValue, String endpoint){
        response = request.when().get(endpoint);
        List<String> jsonResponse = response.jsonPath().getList("address.street");
//        String actualValue = jsonResponse.get(0);
//        assertEquals(expectedValue,actualValue);
        assertTrue("The value " + expectedValue + " is not on the list.",jsonResponse.contains(expectedValue));
    }
}
