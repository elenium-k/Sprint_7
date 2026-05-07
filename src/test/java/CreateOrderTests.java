import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import service.OrderClient;


import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static utils.ApiConfig.BASE_URI;

@RunWith(Parameterized.class)
public class CreateOrderTests {

    private String[] colors;

    public CreateOrderTests(String[] colors) {
        this.colors = colors;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{}},
                {new String[]{"BLACK", "GREY"}}
        });
    }

    @Test
    @DisplayName("Create order tests")
    public void createOrderTests() {
        Order order = new Order(
                "ivan",
                "ivanov",
                "test address",
                5,
                "+79991234567",
                8,
                "2026-06-06",
                "I love scooters",
                colors
        );

        OrderClient orderClient = new OrderClient();
        Response response = orderClient.createOrder(order);
        response.then()
                .statusCode(201)
                .body("track", notNullValue())
                .body("track", instanceOf(Integer.class));

        int trackId = response.jsonPath().getInt("track");
        System.out.println(trackId);
    }
}
