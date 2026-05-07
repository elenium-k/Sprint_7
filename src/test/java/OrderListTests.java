import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import service.OrderListClient;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static utils.ApiConfig.BASE_URI;


public class OrderListTests {

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
    }

    @Test
    @DisplayName("Order list test")
    public void orderListTest() {
        OrderListClient orderListClient = new OrderListClient();

        Response response = orderListClient.listOrder();

        response.then().statusCode(200);
        List<?> orders = response.jsonPath().getList("orders");
        assertNotNull(orders);

        System.out.println(response.getStatusCode());
        System.out.println(response.getBody().prettyPrint());
    }
}

