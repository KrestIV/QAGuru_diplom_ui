package steps;

import io.qameta.allure.Step;
import models.LoginBodyModel;
import models.LoginResponseModel;
import models.ProfileResponseModel;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.RequestSpec.requestWithContentSpec;
import static specs.RequestSpec.responseSpec;

public class AccountApiSteps {

    public LoginResponseModel loginResponse;

    @Step("Генерация токена авторизации пользователя")
    public AccountApiSteps generateToken(LoginBodyModel authData) {
        given(requestWithContentSpec)
                .body(authData)
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .spec(responseSpec(200));
        return this;
    }

    @Step("Авторизация пользователя на портале")
    public AccountApiSteps login(LoginBodyModel authData) {
        loginResponse = given(requestWithContentSpec)
                .body(authData)
                .when()
                .post("/Account/v1/login")
                .then()
                .spec(responseSpec(200))
                .extract().as(LoginResponseModel.class);
        return this;
    }

    @Step("Проверка удаления книги из библиотеки пользователя")
    public AccountApiSteps libraryMustBeEmptyCheck() {
        ProfileResponseModel profileResponse =
                given(requestWithContentSpec)
                        .header("authorization", "Bearer " + loginResponse.getToken())
                        .when()
                        .get("/Account/v1/User/" + loginResponse.getUserId())
                        .then()
                        .spec(responseSpec(200))
                        .extract().as(ProfileResponseModel.class);

        assertThat(profileResponse.getBooks()).isEmpty();

        return this;
    }
}
