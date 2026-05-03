import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Courier;
import model.CourierCreds;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import service.CourierClient;
import service.CourierGenerator;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class CourierLoginTest {

    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";

    private final String login;
    private final String password;
    private final int expectedStatusCode;
    private String courierId;

    private CourierClient courierClient;

    public CourierLoginTest(String login, String password, int expectedStatusCode) {
        this.login = login;
        this.password = password;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
        courierClient = new CourierClient();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData() {

        Courier rightCourier = CourierGenerator.randomCourier();
        Courier wrongCourier = new Courier().withLogin("1234").withPassword("4321");
        Courier emptyLogin = new Courier().withLogin("").withPassword("passцщкв");
        Courier emptyPassword = new Courier().withLogin("login").withPassword("");

        return Arrays.asList(new Object[][]{
                {rightCourier.getLogin(), rightCourier.getPassword(), 200},
                {wrongCourier.getLogin(), wrongCourier.getPassword(), 404},
                {emptyLogin.getLogin(), emptyLogin.getPassword(), 400},
                {emptyPassword.getLogin(), emptyPassword.getPassword(), 400}
        });
    }

    @Test
    @DisplayName("Login courier param tests")
    public void loginCourierParamTests() {
        if (expectedStatusCode == 200) {
            Courier courier = new Courier()
                    .withLogin(login)
                    .withPassword(password)
                    .withFirstName("Персефоний");

            Response createResponse = courierClient.create(courier);
            createResponse.then().statusCode(201);
            courierId = createResponse.jsonPath().getString("id");
        }

        Response response = courierClient.login(new CourierCreds(login, password));
        assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @After
    public void deleteCourier() {

        if (courierId != null) {
            courierClient.delete(courierId);
            courierId = null;
        }
    }
}
