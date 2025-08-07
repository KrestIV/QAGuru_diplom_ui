package steps;

import io.qameta.allure.Step;
import io.restassured.http.Cookies;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.RequestSpec.requestWithContentSpec;
import static specs.RequestSpec.responseSpec;

public class LoginAPISteps {

    private Response response;

    @Step("Авторизовать пользователя")
    public LoginAPISteps login(Map<String, String> authFormData) {
        response = given(requestWithContentSpec)
                .formParams(authFormData)
                .when()
                .post("/users/login_do/");

        response.then()
                .spec(responseSpec(301));

        return this;
    }

    @Step("Авторизовать пользователя с неверным паролем")
    public LoginAPISteps loginWithWrongPassword(Map<String, String> authFormData) {
        response = given(requestWithContentSpec)
                .formParams(authFormData)
                .when()
                .post("/users/login_do/");

        response.then()
                .spec(responseSpec(200));

        return this;
    }

    @Step("Проверить авторизацию пользователя")
    public LoginAPISteps checkLoginError() {
        String html = response.getBody().asString();
        assertThat(html)
                .contains("неверный логин или пароль");

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
