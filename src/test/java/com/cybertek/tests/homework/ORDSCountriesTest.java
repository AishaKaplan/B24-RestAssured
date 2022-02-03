package com.cybertek.tests.homework;

import com.cybertek.tests.ORDSTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ORDSCountriesTest extends ORDSTestBase {
    @DisplayName("Q1")
    @Test
    public void ORDSGetUSTest() {
        /**
         * Q1:
         * - Given accept type is Json
         * - Path param value- US
         * - When users sends request to /countries
         * - Then status code is 200
         * - And Content - Type is Json
         * - And country_id is US
         * - And Country_name is United States of America
         * - And Region_id is
         */

        Response response = given().accept(ContentType.JSON)
                .and().when().get("/countries/US");
        System.out.println("status code = " + response.statusCode());
        assertEquals(200, response.statusCode());
        response.prettyPrint();

        System.out.println("contentType = " + response.contentType());
        assertEquals("application/json" ,  response.contentType());
        JsonPath json=response.jsonPath();
        String country_id= json.getString("country_id");
        System.out.println("country_id = " + country_id);
        assertEquals("US", country_id);
        String country_name=json.getString("country_name");
        assertEquals("United States of America", country_name);
        int region_id=json.getInt("region_id");
        assertEquals(2, region_id);

    }
    /**
     * Q2:
     * - Given accept type is Json
     * - Query param value - q={"department_id":80}
     * - When users sends request to /employees
     * - Then status code is 200
     * - And Content - Type is Json
     * - And all job_ids start with 'SA'
     * - And all department_ids are 80
     * - Count is 25
     */
@DisplayName("Q2")
    @Test
    public void GetDepartmentTest(){
    Response response=given().accept(ContentType.JSON)
            .and().queryParams("q", "{\"department_id\" : 80}")
            .when().get("/employees");
    assertEquals(HttpStatus.SC_OK, response.statusCode());
    response.prettyPrint();
    assertEquals("application/json", response.contentType());
    JsonPath json=response.jsonPath();
    List<String> allJobIDs=json.getList("items.findAll{it.job_id.startsWith('SA')}.job_id");
    System.out.println("allJobIDs = " + allJobIDs);
   allJobIDs.forEach(e->assertTrue(e.startsWith("SA"))
   );
    List<Integer> ids=json.getList("items.findAll{it.department_id=80}.department_id");
    System.out.println("ids = " + ids);
    ids.forEach(e->assertTrue(e==80));
    System.out.println("all ids count = " + ids.size());
    assertEquals(25, ids.size(), "Count is not matching");

}
/**
 *Q3:
 * - Given accept type is Json
 * -Query param value q= region_id 3
 * - When users sends request to /countries
 * - Then status code is 200
 * - And all regions_id is 3
 * - And count is 6
 * - And hasMore is false
 * - And Country_name are;
 * Australia,China,India,Japan,Malaysia,Singapore
 *
 */
@DisplayName("Q3 Test")
    @Test
    public void FarCountriesTest(){
    Response response=given().accept(ContentType.JSON)
            .and().queryParams("q", "{\"region_id\" : 3}")
            .when().get("/countries");
    System.out.println("statusCode = " + response.statusCode());
    response.prettyPrint();
    assertEquals(200, response.statusCode());
    System.out.println("Content type = "+ response.contentType());
    assertEquals("application/json", response.contentType());
    JsonPath json=response.jsonPath();
    List<Integer> allId=json.getList("items.findAll{it.region_id=3}.region_id");
   // allId.forEach(e->assertTrue(e==3));
    for (Integer each : allId) {
        assertEquals(3, each);
    }
    assertEquals(6, allId.size(), "Failed");

   // System.out.println("allId count = " + allId.size());
   // assertTrue(allId.size()==6, "Size is not correct");

    List<Object> list = json.getList("items.regions_id");
          assertEquals(6, list.size());
    System.out.println("list size = " + list.size());

        //  String count = json.getString("count");
       // assertEquals("6", count);
    //System.out.println("get string count = "+ count);

    assertEquals(false, json.getBoolean("hasMore"));
    System.out.println(json.getBoolean("hasMore"));

    List<String> countryNames=json.getList("items.country_name");
    System.out.println("countryNames = " + countryNames);
    List<String> expCountries = new ArrayList<>(Arrays.asList("Australia", "China", "India", "Japan", "Malaysia", "Singapore"));
    assertEquals(expCountries, countryNames, "Name are not matching");

}
}

