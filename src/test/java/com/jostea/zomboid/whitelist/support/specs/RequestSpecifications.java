package com.jostea.zomboid.whitelist.support.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public class RequestSpecifications {

    private RequestSpecifications() {
    }

    public static RequestSpecification defaultRequestSpec() {

        return new RequestSpecBuilder()
                .setContentType(JSON)
                .setAccept(JSON)
                .build();
    }
}