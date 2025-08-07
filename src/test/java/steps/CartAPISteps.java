package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.RequestSpec.*;

public class CartAPISteps {

    private Response response;

    @Step("Получить содержимое корзины")
    public CartAPISteps getCart(Map<String, String> cookies) {
        response = given(requestNoContentSpec)
                .cookies(cookies)
                .when()
                .get("/emarket/cart/");

        response.then()
                .spec(responseSpec(200));

        return this;
    }

    @Step("Очистить корзину")
    public CartAPISteps clearCart(Map<String, String> cookies) {
        response = given(requestNoContentSpec)
                .cookies(cookies)
                .when()
                .get("/emarket/basket/remove_all/");

        response.then()
                .spec(responseSpec(200));

        return this;
    }

    @Step("Добавить товар в корзину")
    public CartAPISteps putItemToCart(Map<String, String> cookies, String item) {
        response = given(requestNoContentSpec)
                .cookies(cookies)
                .when()
                .get("/emarket/basket/put/element/" + item + "/");

        response.then()
                .spec(responseSpec(200));

        return this;
    }

    @Step("Проверить содержимое корзины")
    public CartAPISteps checkCart(String searchString) {
        String html = response.getBody().asString();
        assertThat(html)
                .contains(searchString);
        return this;
    }
}
