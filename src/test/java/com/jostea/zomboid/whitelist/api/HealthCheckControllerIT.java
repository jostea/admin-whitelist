package com.jostea.zomboid.whitelist.api;

import com.jostea.zomboid.whitelist.base.BaseApiIT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.jostea.zomboid.whitelist.support.specs.RequestSpecifications.defaultRequestSpec;
import static io.restassured.RestAssured.given;

class HealthCheckControllerIT extends BaseApiIT {

    @Test
    @DisplayName("Should return 200 and alive message")
    void shouldReturn200WhenHealthCheckIsCalled() {

        given()
                .spec(defaultRequestSpec())
                .when()
                .get("/health-check")
                .then()
                .statusCode(200)
                .body(org.hamcrest.Matchers.equalTo("I'm alive, motherfucker!"));
    }
}