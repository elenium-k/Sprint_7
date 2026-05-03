package service;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;


public class OrderListClient {

    private static final String ORDER_LIST_ENDPOINT = "api/v1/orders";

    public Response listOrder() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(ORDER_LIST_ENDPOINT);

    }
}
