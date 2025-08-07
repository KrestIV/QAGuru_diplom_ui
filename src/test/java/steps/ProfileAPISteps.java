package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.RequestSpec.*;

public class ProfileAPISteps {

    private Response response;

    @Step("Авторизовать пользователя")
    public ProfileAPISteps getSettings(Map<String, String> cookies) {
        response = given(requestNoContentSpec)
                .cookies(cookies)
                .when()
                .get("/users/settings/");

        response.then()
                .spec(responseSpec(200));

        return this;
    }

    @Step("Авторизовать пользователя с неверным паролем")
    public ProfileAPISteps changeSettings(Map<String, String> settingsFormData, Map<String, String> cookies) {
        response = given(requestWithContentSpec)
                .formParams(settingsFormData)
                .cookies(cookies)
                .when()
                .post("/users/settings_do/");

        response.then()
                .spec(responseSpec(301));

        return this;
    }

    @Step("Проверить авторизацию пользователя")
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
