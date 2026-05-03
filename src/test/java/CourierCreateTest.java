import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import service.CourierClient;
import service.CourierGenerator;
import java.util.Arrays;
import java.util.Collection;
import static org.hamcrest.core.IsEqual.equalTo;
import org.junit.Test;



@RunWith(Parameterized.class)
public class CourierCreateTest {

    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";

    private final String login;
    private final String password;
    private final String firstName;
    private final int expectedStatusCode;
    private String courierId;

    private CourierClient courierClient;

    public CourierCreateTest(String login, String password, String firstName,
                             int expectedStatusCode) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
        courierClient = new CourierClient();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        Courier courier1 = CourierGenerator.randomCourier();
        Courier courier2 = CourierGenerator.withNullLogin();
        Courier courier4 = CourierGenerator.withNullPassword();

        return Arrays.asList(new Object[][]{

                {courier1.getLogin(), courier1.getPassword(), courier1.getFirstName(), 201},
                {courier2.getLogin(), courier2.getPassword(), courier2.getFirstName(), 400},
                {courier4.getLogin(), courier4.getPassword(), courier4.getFirstName(), 400},
        });
    }

    @Test
    @DisplayName("Create courier param tests")
    public void createCourierParamTests() {

        Courier courier = new Courier(login, password, firstName);
        Response response = courierClient.create(courier);

        response.then().statusCode(equalTo(expectedStatusCode));

        if (response.getStatusCode() == 201) {
            JsonPath jsonPath = response.jsonPath();
            courierId = jsonPath.getString("courierId");
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
