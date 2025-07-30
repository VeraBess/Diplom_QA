package ru.netology.service.db;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.netology.service.data.DataHelper;

import static io.restassured.RestAssured.given;

public class ApiData {
    private static final String baseUri = "http://localhost:8080";

    private ApiData() {}

    public static Response buyTour(DataHelper.CardData cardData) {
        return given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .body(cardData)
                .when()
                .post("/api/v1/pay");
    }
}
