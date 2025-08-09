package steps;

import helpers.CookieStorage;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.RequestSpec.*;

public class ProfileAPISteps {

    private Response response;

    @Step("Получить настройки профиля")
    public ProfileAPISteps getSettings() {
        response = given(requestNoContentSpec)
                .cookies(CookieStorage.getCookies())
                .when()
                .get("/users/settings/");

        response.then()
                .spec(responseSpec(200));

        return this;
    }

    @Step("Изменить настройки профиля")
    public ProfileAPISteps changeSettings(Map<String, String> settingsFormData) {
        response = given(requestWithContentSpec)
                .formParams(settingsFormData)
                .cookies(CookieStorage.getCookies())
                .when()
                .post("/users/settings_do/");

        response.then()
                .spec(responseSpec(301));

        return this;
    }

    @Step("Проверить внесенные в профиль изменения")
    public ProfileAPISteps checkSettings() {
        String html = response.getBody().asString();
        assertThat(html)
                .contains("Тамерланович");

        return this;
    }

    public Map<String, String> getNewSettingsFormData(){
        Map<String, String> settingsFormData = new HashMap<>();
        settingsFormData.put("password", "");
        settingsFormData.put("password_confirm", "");
        settingsFormData.put("email", "tegir_st@mail.ru");
        settingsFormData.put("data[720575][lname]", "");
        settingsFormData.put("data[720575][fname]", "степан");
        settingsFormData.put("data[720575][father_name]", "Тамерланович");

        return settingsFormData;
    }

    public Map<String, String> getOldSettingsFormData(){
        Map<String, String> settingsFormData = new HashMap<>();
        settingsFormData.put("password", "");
        settingsFormData.put("password_confirm", "");
        settingsFormData.put("email", "tegir_st@mail.ru");
        settingsFormData.put("data[720575][lname]", "");
        settingsFormData.put("data[720575][fname]", "степан");
        settingsFormData.put("data[720575][father_name]", "");

        return settingsFormData;
    }


}
