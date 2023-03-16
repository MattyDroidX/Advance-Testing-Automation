package steps;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Base64;

import static io.restassured.RestAssured.given;

public class ExamplesAPI {

    public void GETRequest(){
        given().baseUri("https://api.github.com")
                .when().get("/users/MattyDroidX/repos").statusCode();
    }

    public void POSTRequest(){
        given().baseUri("baseUri")
                .when().post("","");
    }

    public void PUTRequest(){
        given().baseUri("baseUri")
                .when().put("","");
    }

    public void DELETERequest(){
        given().baseUri("")
                .when().delete();
    }

    public void SOADRequest(){
        String xmlEnvelope = "<soap12:Envelope xlmns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">" +
                "<soap12:Body>" +
                "<Define xmlns=\"http://services.aonaware.com/webservices/\">" +
                "<word></word>" +
                "</Define>" +
                "</soap12:Body>" +
                "</soap12:envelope>";
        given().header("SOAPAction", "Define")
                .baseUri("https://api.blogExample.com")
                .when().body(xmlEnvelope).post("/endpoint");
    }

    public void basicAuth(String user, String password){
        given().auth().basic(user,password)
                .when().get("AUTH_ENDPOINT").then().assertThat()
                .statusCode(200);

    }

    public void formAuth(String user, String password){
        given().auth().form(user,password)
                .when().get("SERVICE").then().assertThat()
                .statusCode(200);
    }

    /*
    1. Obtain the code of the original service to obtain the token
    2. Obtain the token, doing a transaction of that token
    3. Access to the protect resource with our token
     */

    public static String clientID;
    public static String redirectUri;
    public static String scope;
    public static String userName;
    public static String password;
    public static String grantType;

    public static String encode(String str1, String str2){
        return new String (Base64.getEncoder().encode((str1 + ":" +str2).getBytes()));
    }

    public static Response getCode(){
        String authorization = encode(userName,password);
        return given().header("authorization", "Basic" + authorization)
                .contentType(ContentType.URLENC)
                .formParam("response type", "code")
                .queryParam("client_id", clientID)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("scope", scope)
                .post("oauth/authorize")
                .then().statusCode(200)
                .extract().response();
    }

    public String parseForOAuthCode(Response response){
        return response.jsonPath().getString("code");
    }

    public static Response getToken(String authCode){
        String authorization = encode(userName,password);
        return given().header("authorization", "Basic" + authorization)
                .contentType(ContentType.URLENC)
                .formParam("response type", authCode)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("grant_type", grantType)
                .post("oauth/token")
                .then().statusCode(200)
                .extract().response();
    }

    public static String parseForToken(Response loginResponse){
        return loginResponse.jsonPath().getString("access_token");
    }

    public static void getFinalService(){
        given().auth().oauth2("access_token").when()
                .get("/service")
                .then()
                .statusCode(200);
    }


}
