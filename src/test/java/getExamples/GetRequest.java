package getExamples;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.Matchers.*;

public class GetRequest {

	@Test
	public void NonBDDGetBookingIds_VerifyStatusCode() {
		//Specify base URI
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";

		//creata a request specification object
		RequestSpecification httpRequest=RestAssured.given();
		//Response object
		Response response=httpRequest.request(Method.GET,"/booking");

		//print response in console window

		String responseBody=response.getBody().asString();
		System.out.println("Response Body is:" +responseBody);

		//status line verification
		String statusLine=response.getStatusLine();
		System.out.println("Status line is:"+statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");

		//status code verification
		int statusCode =  response.getStatusCode();
		System.out.println("Status code is:"+statusCode);
		Assert.assertEquals(statusCode, 200);	
	}

	@Test
	public void BDDGetBookingIds_VerifyStatusCode() {		
		// Given
		RestAssured.given()
		.baseUri("https://restful-booker.herokuapp.com")
		// When
		.when()
		.get("/booking")
		// Then
		.then()
		.statusCode(200)
		.statusLine("HTTP/1.1 200 OK")
		// To verify booking count
		.body("bookingid.sum()", Matchers.hasSize(55))
		// To verify booking id at index 3
		.body("bookingid[3]", Matchers.equalTo(10));		
	}
	
	@Test
	public void BDDGetBookingIds_VerifyStatusCode1() {		
		RestAssured.given()
		.baseUri("https://restful-booker.herokuapp.com")
		.when()
		.get("/booking")
		.then()
		.statusCode(200)
		.statusLine("HTTP/1.1 200 OK")
		// To verify booking id at index 3
		.body("bookingid[3]", equalTo(10))
		// To verify booking count
		.body("bookingid", hasSize(10));
	}
}
