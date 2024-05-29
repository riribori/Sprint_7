
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.CreateCourier;
import org.example.LoginCourier;
import org.example.Steps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TestCreateCourier {

    private Steps steps;
    private String login;
    private String password;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        steps = new Steps();
    }


    @Test
    @DisplayName("Сreate New Сourier") // имя теста
    @Description("Basic test for api/v1/courier endpoint") // описание теста
    public void createNewСourierTest() {
        CreateCourier createCourier = new CreateCourier(RandomStringUtils.randomAlphabetic(255), RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(255));
        steps.createNewСourier(createCourier).then().assertThat().body("ok", is(true)) //тест на создание
                .and()
                .statusCode(201);// тест на код
        login = createCourier.getLogin();
        password = createCourier.getPassword();
    }

    @Test
    @DisplayName("Сreate New Сourier Without Login")
    @Description("Test for check create Courier without login")
    public void createNewСourierWithOutLoginTest() {
        CreateCourier createCourier = new CreateCourier(null, RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(255));
        steps.createNewСourier(createCourier).then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400); // тест на код
    }

    @Test
    @DisplayName("Сreate Double Сourier")
    @Description("Test for check Double Courier")
    public void createDoubleСourierTest() {
        CreateCourier createCourier = new CreateCourier(RandomStringUtils.randomAlphabetic(255), RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(255));
        steps.createNewСourier(createCourier);
        steps.createNewСourier(createCourier).then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
    }

    @Test
    @DisplayName("Сreate Double login Сourier")
    @Description("Test for check Double login Courier")
    public void createDoubleLoginСourierTest() {
        CreateCourier createCourier = new CreateCourier(RandomStringUtils.randomAlphabetic(255), RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(255));
        steps.createNewСourier(createCourier);
        CreateCourier createCourierDoubleLogin = new CreateCourier(createCourier.getLogin(), RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(255));
        steps.createNewСourier(createCourierDoubleLogin).then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
    }

    @After
    public void deleteCourier() {
        if (login != null && password != null) {
            Response response = steps.loginСourier(new LoginCourier(login, password));
            String id = response.jsonPath().getString("id");
            System.out.println(id);
            steps.deleteСourier(id);
            login = null;
            password = null;
        }
    }
}
