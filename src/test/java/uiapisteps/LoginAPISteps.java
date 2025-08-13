package uiapisteps;

import helpers.CookieStorage;
import io.qameta.allure.Step;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import models.AuthDataModel;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static specs.RequestSpec.requestWithFormContentSpec;
import static specs.RequestSpec.responseSpec;

public class LoginAPISteps {

    private Response response;

    @Step("Авторизовать пользователя")
    public LoginAPISteps receiveCookies(AuthDataModel authDataContainer) {
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
