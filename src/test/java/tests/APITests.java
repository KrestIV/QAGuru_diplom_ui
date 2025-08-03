package tests;

import io.restassured.http.Cookies;
import io.restassured.response.Response;
import models.LoginBodyModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.RequestSpec.*;

public class APITests extends APIBaseTests{

    @Test
    public void authorizeWithWrongPasswordShouldReturnErrorTest() {
        String login = "tegir_st";
        String pw = "RJSPFPyL8hLgekC" + "1";
        LoginBodyModel authData = new LoginBodyModel(login, pw, "/");
        Response response = given(requestWithContentSpec)
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                //.body(authData)
                .formParam("login", login)
                .formParam("password", pw)
                .formParam("from_page", "/")
                .when()
                .post("/users/login_do/");


        response.then()
                .spec(responseSpec(200));

        String html = response.getBody().asString();
        assertThat(html)
                .contains("неверный логин или пароль");
    }

    @Test
    public void addedItemMustBeInCartTest(){
        //authorize
        String login = "tegir_st";
        String pw = "RJSPFPyL8hLgekC";
        LoginBodyModel authData = new LoginBodyModel(login, pw, "/");
        Response response = given(requestWithContentSpec)
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                //.body(authData)
                .formParam("login", login)
                .formParam("password", pw)
                .formParam("from_page", "/")
                .when()
                .post("/users/login_do/");


        response.then()
                .spec(responseSpec(301));

        Cookies cookies = response.getDetailedCookies();
        String phpSessId = cookies.getValue("PHPSESSID");
        String statId = cookies.getValue("stat_id");

        //clean cart
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/emarket/basket/remove_all/");

        response.then()
                .spec(responseSpec(200));

        //add item
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/emarket/basket/put/element/3285/");

        response.then()
                .spec(responseSpec(200));

        //check cart
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/emarket/cart/");

        response.then()
                .spec(responseSpec(200));

        String html = response.getBody().asString();
        assertThat(html)
                .contains("Brit Fresh Chicken");

        //clean cart
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/emarket/basket/remove_all/");

        response.then()
                .spec(responseSpec(200));

    }

    @Test
    public void afterCleaningCartMustBeEmptyTest(){
        //authorize
        String login = "tegir_st";
        String pw = "RJSPFPyL8hLgekC";
        LoginBodyModel authData = new LoginBodyModel(login, pw, "/");
        Response response = given(requestWithContentSpec)
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                //.body(authData)
                .formParam("login", login)
                .formParam("password", pw)
                .formParam("from_page", "/")
                .when()
                .post("/users/login_do/");


        response.then()
                .spec(responseSpec(301));

        Cookies cookies = response.getDetailedCookies();
        String phpSessId = cookies.getValue("PHPSESSID");
        String statId = cookies.getValue("stat_id");

        //add item
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/emarket/basket/put/element/3158/");

        response.then()
                .spec(responseSpec(200));

        //clean cart
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/emarket/basket/remove_all/");

        response.then()
                .spec(responseSpec(200));

        //check cart
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/emarket/cart/");

        response.then()
                .spec(responseSpec(200));

        String html = response.getBody().asString();
        assertThat(html)
                .contains("Корзина пуста");
    }

    @Test
    public void addingNonexistentItemToCartMustReturnErrorTest(){
        //authorize
        String login = "tegir_st";
        String pw = "RJSPFPyL8hLgekC";
        LoginBodyModel authData = new LoginBodyModel(login, pw, "/");
        Response response = given(requestWithContentSpec)
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                //.body(authData)
                .formParam("login", login)
                .formParam("password", pw)
                .formParam("from_page", "/")
                .when()
                .post("/users/login_do/");


        response.then()
                .spec(responseSpec(301));

        Cookies cookies = response.getDetailedCookies();
        String phpSessId = cookies.getValue("PHPSESSID");
        String statId = cookies.getValue("stat_id");

        //clean cart
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/emarket/basket/remove_all/");

        response.then()
                .spec(responseSpec(200));

        //add nonexistent item and check error
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/emarket/basket/put/element/999999999/");

        response.then()
                .spec(responseSpec(200));

        //check cart
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/emarket/cart/");

        response.then()
                .spec(responseSpec(200));

        String html = response.getBody().asString();
        assertThat(html)
                .contains("Корзина пуста");
    }

    @Test
    public void changingProfileMustBeSavedInProfileTest(){
        //authorize
        String login = "tegir_st";
        String pw = "RJSPFPyL8hLgekC";
        LoginBodyModel authData = new LoginBodyModel(login, pw, "/");
        Response response = given(requestWithContentSpec)
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                //.body(authData)
                .formParam("login", login)
                .formParam("password", pw)
                .formParam("from_page", "/")
                .when()
                .post("/users/login_do/");


        response.then()
                .spec(responseSpec(301));

        Cookies cookies = response.getDetailedCookies();
        String phpSessId = cookies.getValue("PHPSESSID");
        String statId = cookies.getValue("stat_id");

        //change profile
        response = given(requestWithContentSpec)
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("password", "")
                .formParam("password_confirm", "")
                .formParam("email", "tegir_st@mail.ru")
                .formParam("data[720575][lname]", "")
                .formParam("data[720575][fname]", "степан")
                .formParam("data[720575][father_name]", "Тамерланович")
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .post("/users/settings_do/");

        response.then()
                .spec(responseSpec(301));

        //check profile
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/users/settings/");

        response.then()
                .spec(responseSpec(200));

        String html = response.getBody().asString();
        assertThat(html)
                .contains("Тамерланович");

        //change profile back
        response = given(requestWithContentSpec)
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("password", "")
                .formParam("password_confirm", "")
                .formParam("email", "tegir_st@mail.ru")
                .formParam("data[720575][lname]", "")
                .formParam("data[720575][fname]", "степан")
                .formParam("data[720575][father_name]", "")
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .post("/users/settings_do/");

        response.then()
                .spec(responseSpec(301));
    }
}
