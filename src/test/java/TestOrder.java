import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.example.Color;
import org.example.CreateOrder;
import org.example.Steps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class TestOrder {
    private final Color[] color;
    private static Steps steps;

    public TestOrder(Color[] color) {
        this.color = color;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        steps = new Steps();
    }

    @Parameterized.Parameters
    public static Object [][] data () {
        return new Object [][] {
                {new Color[]{Color.BLACK}},
                {new Color[]{Color.GRAY}},
                {new Color[]{Color.GRAY,Color.BLACK}},
                {new Color[]{}},
                {null}
        };
    }

    @Test
    @DisplayName("Create order test")
    @Description("Test for create order")
            public void createOrderTest(){
        CreateOrder createOrder = new CreateOrder("Имя", "Фамилия", "Aдрес", "1", "88888888888", 2, "2024-05-29T21:00:00.000Z", "Комментарий", color);
        steps.createOrder(createOrder).then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
    }



}
