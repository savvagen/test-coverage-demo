package com.example.services;

import com.example.filters.MyAllureRestAssured;
import com.example.models.User;
import com.github.viclovsky.swagger.coverage.SwaggerCoverageRestAssured;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class UserService {

    public RequestSpecification requestSpec;

    public UserService(){
        requestSpec = RestAssured.given()
                .relaxedHTTPSValidation()
                .filters(new RequestLoggingFilter(),
                        new ResponseLoggingFilter(),
                        new AllureRestAssured(),
                        new SwaggerCoverageRestAssured(), new MyAllureRestAssured())
                .accept("application/json")
                .contentType(ContentType.JSON);
    }

    public Response createUser(User user){
        return requestSpec.body(user).when().post("/user");
    }

    public Response getUser(String username){
        return requestSpec.when().pathParam("username", username).get("/user/{username}");
    }

}
