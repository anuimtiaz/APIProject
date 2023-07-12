import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import APIPayloads.Payloads;

public class APIBasics {

	public static void main(String[] args) {
	// validate if Add Place API is working as expected
	//Add place-> Update Place with New Address -> Get place to validate if New address is present in responses
	// Validate if Add API is working as expected
	// Given - all input details
	// When - Submit the API - resource, http methods goes under when 
	// Then - Validate the response
		
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		
		String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(Payloads.AddPlace()).when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("server", "Apache/2.4.41 (Ubuntu)").extract().response().asString();
		
		
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String placeid = js.getString("place_id");
		
		System.out.println(placeid);
		
	//Update Place
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\n"
				+ "\"place_id\":\""+placeid+"\",\n"
				+ "\"address\":\"70 Summer walk, USA\",\n"
				+ "\"key\":\"qaclick123\"\n"
				+ "}\n"
				+ "")
		.when().put("/maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
	
		
	}

}
