package com.jostea.zomboid.whitelist.base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseApiIT {

    @LocalServerPort
    protected int port;

    @BeforeEach
    void setUpRestAssured() {
        RestAssured.port = port;
    }
}