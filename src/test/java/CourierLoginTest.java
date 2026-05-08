import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Courier;
import model.CourierCreds;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import service.CourierClient;
import service.CourierGenerator;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static utils.ApiConfig.BASE_URI;

@RunWith(Parameterized.class)
public class CourierLoginTest {

    private final String login;
    private final String password;
    private final int expectedStatusCode;
    private String courierId;

    private CourierClient courierClient;
    private static String testCourierId;

    public CourierLoginTest(String login, String password, int expectedStatusCode) {
        this.login = login;
        this.password = password;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
        courierClient = new CourierClient();
        Courier testCourier = CourierGenerator.testCourier();
        Response createResponse = courierClient.create(testCourier);
        testCourierId = createResponse.jsonPath().getString("id");
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                // Успешный логин
                {"Persefoniy_Scooter_God", "password123", 200},
                // Ошибка в логине
                {"wrong_Persefoniy_Scooter_God", "password123", 404},
                // Ошибка в пароле
                {"Persefoniy_Scooter_God", "wrong_password123", 404},
                // Пустой логин
                {"", "password123", 400},
                // Пустой пароль
                {"Persefoniy_Scooter_God", "", 400}
        });
    }

    @Test
    @DisplayName("Login courier param tests")
    public void loginCourierParamTests() {
        Response response = courierClient.login(new CourierCreds(login, password));
        assertEquals(expectedStatusCode, response.getStatusCode());

        if (expectedStatusCode == 200) {
            courierId = response.jsonPath().getString("id");
        }
    }

    @After
    public void deleteCourier() {

        if (courierId != null) {
            courierClient.delete(courierId);
            courierId = null;
        }
    }
}
