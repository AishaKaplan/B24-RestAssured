package com.cybertek.tests.homework;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ZipCodeTest {
    @BeforeAll
    public static void setUp() {
        System.out.println("Set up method: assigning value to baseURI variable");
        baseURI = "https://api.zippopotam.us";
    }
        /**
         * Given Accept application/json
         * And path zipcode is 22031
         * When I send a GET request to /us endpoint
         * Then status code must be 200
         * And content type must be application/json
         * And Server header is cloudflare
         * And Report-To header exists
         * And body should contains following information
         *     post code is 22031
         *     country  is United States
         *     country abbreviation is US
         *     place name is Fairfax
         *     state is Virginia
         *     latitude is 38.8604
         */

        @DisplayName("Q1")
                @Test
        public void ZipCode1Test(){
            Response response=given().accept(ContentType.JSON)
                    .when().get("/us/22031");

            System.out.println(response.statusCode());
            assertEquals("200", response.statusCode());
            System.out.println(response.prettyPrint());

        }
    }
