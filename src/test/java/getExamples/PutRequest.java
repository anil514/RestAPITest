package getExamples;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class PutRequest {
	@Test
	public void NonBDDStylePostRequest() {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		RequestSpecification httpRequest = RestAssured.given();
		
		//Request paylaod sending along with post request
		JSONObject requestParams = new JSONObject();
		requestParams.put("firstname", "Anil");
		requestParams.put("lastname", "Potla");
		requestParams.put("totalprice", "514");
		requestParams.put("depositpaid", "true");
		//Json Arry
		requestParams.put("checkin", "2018-01-01");
		requestParams.put("checkin", "2019-01-01");
		JSONArray authArray = new JSONArray();
		authArray.add(requestParams);
		requestParams.put("bookingdates", authArray);
		
		//HashMap
		//Map<String, Object> map = new HashMap<>();
		/* map.put("bookingdates", Arrays.asList(new HashMap<String, Object>(){
			{put("checkin", "2018-01-01");
			put("checkin", "2019-01-01");
			}
		})); */
		
		requestParams.put("additionalneeds", "Breakfast");
		httpRequest.header("Content-Type","application/json");
		httpRequest.cookie("token", "cd44c1a2b400514");
		httpRequest.body(requestParams.toJSONString());
		Response response = httpRequest.request(Method.PUT, "/booking/1");
		String responseBody=response.getBody().asString();
		System.out.println("Response Body is:" +responseBody);
		
	}
	
	@Test
	public void nonBDD() {
		// There is no need to add escape character manually. Just paste string within
		// double
		// quotes. It will automatically add escape sequence as required.
		String jsonString = "{\r\n" + "    \"firstname\" : \"Amod\",\r\n" + "    \"lastname\" : \"Mahajan\",\r\n"
				+ "    \"totalprice\" : 111,\r\n" + "    \"depositpaid\" : true,\r\n" + "    \"bookingdates\" : {\r\n"
				+ "        \"checkin\" : \"2018-01-01\",\r\n" + "        \"checkout\" : \"2019-01-01\"\r\n"
				+ "    },\r\n" + "    \"additionalneeds\" : \"Breakfast\"\r\n" + "}";
 
		// Create a request specification
		RequestSpecification request = RestAssured.given();
 
		// Setting content type to specify format in which request payload will be sent.
		// ContentType is an ENUM.
		request.contentType(ContentType.JSON);
		// Setting a cookie for authentication as per API documentation
		request.cookie("token", "e606912d6597f1f");
		// Adding URI
		request.baseUri("https://restful-booker.herokuapp.com/booking/1");
		// Adding body as string
		request.body(jsonString);
 
		// Calling PUT method on URI. After hitting we get Response
		Response response = request.put();
 
		// Printing Response as string
		System.out.println(response.asString());
 
		// Get Validatable response to perform validation
		ValidatableResponse validatableResponse = response.then();
 
		// Validate status code as 200
		validatableResponse.statusCode(200);
 
		// Validate value of firstName is updated
		validatableResponse.body("firstname", Matchers.equalTo("Amod"));
 
		// Validate value of lastName is updated
		validatableResponse.body("lastname", Matchers.equalTo("Mahajan"));
	}

}
