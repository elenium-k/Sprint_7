package service;
import model.Courier;
import model.CourierCreds;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import io.qameta.allure.Step;

public class CourierClient {
    private static final String CREATE_ENDPOINT = "/api/v1/courier";
    private static final String LOGIN_ENDPOINT = "/api/v1/courier/login";
    private static final String DELETE_ENDPOINT = "/api/v1/courier/:id";

    @Step("Создание курьера")
    public Response create(Courier courier) {
       return given().header("Content-type", "Application/json")
                .body(courier)
                .when()
                .post(CREATE_ENDPOINT);
    }

    @Step("Логин курьера")
    public Response login(CourierCreds courierCreds) {
        return given().header("Content-type", "Application/json")
                .body(courierCreds)
                .when()
                .post(LOGIN_ENDPOINT);
    }

    @Step("Удаление курьера")
    public Response delete(String courierId) {
        return given().header("Content-type", "Application/json")
                .body(courierId)
                .when()
                .post(LOGIN_ENDPOINT);
    }

}
