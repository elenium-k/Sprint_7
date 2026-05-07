package utils;

import io.restassured.RestAssured;
import org.junit.BeforeClass;

public class ApiConfig {
    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";; //

    @BeforeClass
    public static void setupBaseUrl() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.useRelaxedHTTPSValidation();
    }
}