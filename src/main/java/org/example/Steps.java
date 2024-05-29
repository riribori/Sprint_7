package org.example;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class Steps {
    @Step("Create Courier")
    public Response createNewСourier (CreateCourier createCourier){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(createCourier)
                        .when()
                        .post("api/v1/courier");
        return response;
    }

    @Step("Login Courier")
    public Response loginСourier (LoginCourier newLoginCourier){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(newLoginCourier)
                        .when()
                        .post("api/v1/courier/login");
        return response;
    }

    @Step("Create order")
    public Response createOrder (CreateOrder newCreateOrder){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(newCreateOrder)
                        .when()
                        .post("api/v1/orders");
        return response;
    }

    @Step("Order list")
    public Response orderList (){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .when()
                        .get("api/v1/orders");
        return response;
    }

    @Step("Delete courier")
    public Response deleteСourier (String courierNumber){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .when()
                        .delete("api/v1/courier/"+ courierNumber);
        return response;
    }

}
