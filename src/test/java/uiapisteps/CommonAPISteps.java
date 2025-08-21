package uiapisteps;

import helpers.CookieStorage;
import io.qameta.allure.Step;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import models.AuthDataModel;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.RequestSpec.*;

public class CommonAPISteps {
    private Response response;

    @Step("Авторизовать пользователя")
    public CommonAPISteps receiveCookies(AuthDataModel authDataContainer) {
        response = given(requestWithFormContentSpec)
                .formParams(authDataContainer.getAuthData())
                .when()
                .post("/users/login_do/");

        response.then()
                .spec(responseSpec(301));

        CookieStorage.getInstance();
        CookieStorage.setCookies(getCookies());

        return this;
    }

    @Step("Очистить корзину")
    public CommonAPISteps prepareCart() {
        response = given(requestNoContentSpec)
                .cookies(CookieStorage.getCookies())
                .when()
                .get("/emarket/basket/remove_all/");

        response.then()
                .spec(responseSpec(200));

        return this;
    }

    @Step("Добавить товар в корзину")
    public CommonAPISteps putItemToCart(String item, int quantity) {
        for (int i = 0; i < quantity; i++) putItemToCart(item);

        return this;
    }

    @Step("Добавить товар в корзину")
    public CommonAPISteps putItemToCart(String item) {
        response = given(requestNoContentSpec)
                .cookies(CookieStorage.getCookies())
                .when()
                .get("/emarket/basket/put/element/" + item + "/");

        response.then()
                .spec(responseSpec(200));

        return this;
    }

    @Step("Проверить содержимое корзины")
    public CommonAPISteps checkCartEmpty() {
        response = given(requestNoContentSpec)
                .cookies(CookieStorage.getCookies())
                .when()
                .get("/emarket/cart/");

        response.then()
                .spec(responseSpec(200));

        String html = response.getBody().asString();
        assertThat(html)
                .contains("корзина пуста");
        return this;
    }

    @Step("Проверить содержимое корзины")
    public CommonAPISteps checkCartItem(String searchString) {
        response = given(requestNoContentSpec)
                .cookies(CookieStorage.getCookies())
                .when()
                .get("/emarket/cart/");

        response.then()
                .spec(responseSpec(200));

        String html = response.getBody().asString();
        assertThat(html)
                .contains(searchString);
        return this;
    }

    public Map<String, String> getCookies() {

        Cookies cookies = response.getDetailedCookies();
        String phpSessId = cookies.getValue("PHPSESSID");
        String statId = cookies.getValue("stat_id");

        Map<String, String> authCookies = new HashMap<>();
        authCookies.put("PHPSESSID", phpSessId);
        authCookies.put("stat_id", statId);
        return authCookies;
    }
}
