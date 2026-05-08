import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Courier;
import org.junit.After;
import org.junit.Before;
import service.CourierClient;
import service.CourierGenerator;
import static org.hamcrest.core.IsEqual.equalTo;
import static utils.ApiConfig.BASE_URI;
import org.junit.Test;


public class CourierCreateConflictErrorTest {
    private Courier firstCourier;
    private String firstCourierId;
    private CourierClient courierClient;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
        courierClient = new CourierClient();

        firstCourier = CourierGenerator.randomCourier();
        Response response = courierClient.create(firstCourier);

        response.then().statusCode(equalTo(201));

        JsonPath jsonPath = response.jsonPath();
        firstCourierId = jsonPath.getString("courierId");
    }

    @Test
    @DisplayName("Courier create conflict error test")
    public void testCourierCreateConflict() {

        Courier duplicateCourier = new Courier(
                firstCourier.getLogin(),
                firstCourier.getPassword(),
                firstCourier.getFirstName()
        );
        Response response = courierClient.create(duplicateCourier);
        response.then()
                .statusCode(equalTo(409))
                .body("message", equalTo("Этот логин уже используется")); // добавлена проверка тела ответа
    }

    @After
    public void deleteCourier() {
        if (firstCourierId != null) {
            courierClient.delete(firstCourierId.toString());
            firstCourierId = null;
        }
    }
    }
