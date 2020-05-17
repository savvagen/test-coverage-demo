package com.example;

import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    @BeforeAll
    public static void setUp(){
        TestConfig cfg = ConfigFactory.create(TestConfig.class);
        RestAssured.baseURI = cfg.hostname();
    }

}
