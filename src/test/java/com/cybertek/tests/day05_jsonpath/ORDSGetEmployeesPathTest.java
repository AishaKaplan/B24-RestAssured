package com.cybertek.tests.day05_jsonpath;

import com.cybertek.tests.ORDSTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ORDSGetEmployeesPathTest extends ORDSTestBase {
    @Test
    public void getAllITProgrammersTest() {
        // guery parameter with HashMap
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("q", "{\"job_id\":\"IT_PROG\"}");
        Response response = given().accept(ContentType.JSON)
                .and().queryParams(paramMap)
                .when().get("/employees");
        System.out.println("Status code = " + response.statusCode());
        response.prettyPrint();
//print first employees id, first name, last name, email
        System.out.println("first employee id = " + response.path("items[0].employee_id"));
        System.out.println("first emp firstname = " + response.path("items[0].first_name"));
        System.out.println("first employee lastname = " + response.path("items[0].last_name"));
        System.out.println("first emp email = " + response.path("items[0].email"));
        //I want the emails of all IT_Progs, save all emails into List of String
        List<String> allEmails = response.path("items.email");
        System.out.println("count = " + allEmails.size());
        System.out.println("allEmails = " + allEmails);

        //you want to text all IT_Progs, save all phones into List of String
        List<String> allPhones = response.path("items.phone_number");
        System.out.println("all phones = " + allPhones);
        //verify that 590.423.4568 is among phone numbers
        assertTrue(allPhones.contains("590.423.4568"));


    }






}
