import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.CreateCourier;
import org.example.LoginCourier;
import org.example.Steps;
import org.junit.*;

import static org.hamcrest.Matchers.*;

public class TestLoginCourier {

    private static Steps steps;
    private static CreateCourier createCourier;
    private static String id;

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        steps = new Steps();
        createCourier = new CreateCourier(RandomStringUtils.randomAlphabetic(255), RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(255));
        steps.createNewСourier(createCourier);
    }

    @Test
    @DisplayName("Сreate login Сourier")
    @Description("Test for login Courier")
    public void loginCourierTest() {
        LoginCourier loginCourier = new LoginCourier(createCourier.getLogin(), createCourier.getPassword());
        Response response = steps.loginСourier(loginCourier);
        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
        id = response.jsonPath().getString("id");
        System.out.println(id);

    }

    @Test
    @DisplayName("Сreate login Сourier without login")
    @Description("Test for create login Courier without login")
    public void loginCourierWithOutLoginTest() {
        LoginCourier loginCourier = new LoginCourier(null, createCourier.getPassword());
        steps.loginСourier(loginCourier).then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);

    }
    @Test
    @DisplayName("Сreate login Сourier another login")
    @Description("Test for create login Courier with not exist login")
    public void loginCourierAnotherLoginTest() {
        LoginCourier loginCourier = new LoginCourier(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(255));
        steps.loginСourier(loginCourier).then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);

    }

    @AfterClass
    public static void deleteCourier (){
        steps.deleteСourier(id);
    }

}
