import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.example.Steps;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class TestOrderList {

    private Steps steps;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        steps = new Steps();
    }

    @Test
    @DisplayName("Create order list")
    @Description("Test for check list order")
    public void orderListTest(){
        steps.orderList().then().assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }

}
