package testReqres;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.File;

public class testReqres {
    File jsonSchema = new File("src/test/java/jsonSchema/getListUser.json");
    JSONObject bodyObj = new JSONObject();

    @Test // GET positive scenario
    public void testGetAllUserList(){
        RestAssured.given().when().get("https://reqres.in/api/users?page=2")
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("page", Matchers.equalTo(2));
    }

    @Test // GET positive scenario (Validate JSON Schema)
    public void testGetUserValidateJsonSchema(){

        RestAssured.given().when().get("https://reqres.in/api/users?page=2")
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body(JsonSchemaValidator.matchesJsonSchema(jsonSchema));
    }

    @Test // GET negative scenario
    public void testGetNonExistingUser(){
        RestAssured.given().when().get("https://reqres.in/api/users/23")
                .then().log().all()
                .assertThat().statusCode(404);
    }

    @Test // POST positive Scenario
    public void testPostUser(){
        String valueName = "Upi";
        String valueJob = "Gamers";
        bodyObj.put("name", valueName);
        bodyObj.put("job", valueJob);

        RestAssured.given().header("Content-Type","application/json")
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(bodyObj.toString())
                .when().post("https://reqres.in/api/users")
                .then().log().all()
                .assertThat().statusCode(201);
    }

    @Test // POST positive Scenario
    public void testPostRegisterUser(){
        String valueEmail = "michael.lawson@reqres.in";
        String valuePassword = "xzty12";
        bodyObj.put("email", valueEmail);
        bodyObj.put("password", valuePassword);

        RestAssured.given().header("Content-Type","application/json")
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(bodyObj.toString())
                .when().post("https://reqres.in/api/register")
                .then().log().all()
                .assertThat().statusCode(200);
    }

    @Test // POST negative Scenario (No password data)
    public void testPostFailedRegisterUser(){
        String valueEmail = "api@gaming";
        bodyObj.put("email", valueEmail);

        RestAssured.given().header("Content-Type","application/json")
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(bodyObj.toString())
                .when().post("https://reqres.in/api/register")
                .then().log().all()
                .assertThat().statusCode(400);
    }

    @Test
    public void testPutUser() {
        String valueName = "Robert";
        String valueJob = "President";

        bodyObj.put("name", valueName);
        bodyObj.put("job", valueJob);

        RestAssured.given().header("Content-Type", "application/json")
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(bodyObj.toString())
                .when().put("https://reqres.in/api/users/2")
                .then().log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void testPatchUser() {
        String valueName = "John";
        String valueJob = "Vice President";

        bodyObj.put("name", valueName);
        bodyObj.put("job", valueJob);

        RestAssured.given().header("Content-Type", "application/json")
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(bodyObj.toString())
                .when().patch("https://reqres.in/api/users/2")
                .then().log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void testDeleteUser() {

        RestAssured.given().header("Content-Type", "application/json")
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .when().delete("https://reqres.in/api/users/2")
                .then()
                .assertThat().statusCode(204).log().all();
    }

    @Test // Test NORMAL login scenario
    public void testLoginUser() {
        String valueEmail = "michael.lawson@reqres.in";
        String valuePassword = "xzty12";
        bodyObj.put("email", valueEmail);
        bodyObj.put("password", valuePassword);

        RestAssured.given().header("Content-Type","application/json")
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(bodyObj.toString())
                .when().post("https://reqres.in/api/login")
                .then().log().all()
                .assertThat().statusCode(200);
    }

    @Test // Test INCORRECT EMAIL login scenario (negative scenario [edge case])
    public void testLoginIncorrectUserEmail() {
        String valueEmail = "upi.gaming@reqres.in";
        String valuePassword = "xzty12";
        bodyObj.put("email", valueEmail);
        bodyObj.put("password", valuePassword);

        RestAssured.given().header("Content-Type","application/json")
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(bodyObj.toString())
                .when().post("https://reqres.in/api/login")
                .then().log().all()
                .assertThat().statusCode(400);
    }

    @Test // Test INCORRECT password login scenario (negative scenario [edge case])
    public void testLoginForgotToInputPassword() {
        String valueEmail = "robert@president";
        bodyObj.put("email", valueEmail);

        RestAssured.given().header("Content-Type","application/json")
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(bodyObj.toString())
                .when().post("https://reqres.in/api/login")
                .then().log().all()
                .assertThat().statusCode(400);
    }

}