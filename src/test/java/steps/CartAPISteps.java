package steps;

import helpers.CookieStorage;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.RequestSpec.*;

public class CartAPISteps {

    private Response response;

    @Step("Очистить корзину")
    public CartAPISteps prepareCart() {
        response = given(requestNoContentSpec)
                .cookies(CookieStorage.getCookies())
                .when()
                .get("/emarket/basket/remove_all/");

        response.then()
                .spec(responseSpec(200));

        return this;
    }

    @Step("Добавить товар в корзину")
    public CartAPISteps putItemToCart(String item) {
        response = given(requestNoContentSpec)
                .cookies(CookieStorage.getCookies())
                .when()
                .get("/emarket/basket/put/element/" + item + "/");

        response.then()
                .spec(responseSpec(200));

        return this;
    }

    @Step("Проверить содержимое корзины")
    public CartAPISteps checkCart(String searchString) {
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
}
