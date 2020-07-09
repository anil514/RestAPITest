package getExamples;

import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class PostRequest {

	@Test
	public void NonBDDStylePostRequest() {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		RequestSpecification httpRequest = RestAssured.given();
		JSONObject requestParams=new JSONObject();
		requestParams.put("username","admin");
		requestParams.put("password","password123");
		httpRequest.header("Content-Type","application/json");
		httpRequest.body(requestParams.toJSONString()); // attach above data to the request
		Response response = httpRequest.request(Method.POST, "/auth");
		//print response in console window
		String responseBody=response.getBody().asString();
		System.out.println("Response Body is:" +responseBody);
		int statusCode = response.statusCode();
		System.out.println(statusCode);
		ValidatableResponse validatableResponse = response.then();
		// Validate token field is null
		// SInce response is one to one mapping so passing key name will give you value.
		// Below method validates that value of token is not null.
		validatableResponse.body("token", Matchers.notNullValue());

		// Validate token length is 15
		validatableResponse.body("token.length()", Matchers.is(15));

		// Validate token is an alphanumeric value
		validatableResponse.body("token", Matchers.matchesRegex("^[a-z0-9]+$"));
	}

	@Test
	public void BDDStylePostRequest() {
		// There is no need to add escape character manually. Just paste string within double 
		// quotes. It will automatically add escape sequence as required. 
		//String jsonString = "{\"username\" : \"admin\",\"password\" : \"password123\"}";
		//another way Request paylaod sending along with post request
		JSONObject requestParams=new JSONObject();
		requestParams.put("username","admin");
		requestParams.put("password","password123");
		
		RestAssured.given()
		.baseUri("https://restful-booker.herokuapp.com/auth")
		.contentType(ContentType.JSON)
		.body(requestParams.toJSONString())
		.when()
		.post()
		.then()
		.assertThat().statusCode(200)
		.body("token", Matchers.notNullValue())
		.body("token.length()", Matchers.is(15))
		.body("token", Matchers.matchesRegex("^[a-z0-9]+$"));		
	}

}
