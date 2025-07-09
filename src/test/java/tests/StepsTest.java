package tests;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import models.*;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.AddBooksSpec.addBooksSpecRequest;
import static specs.AddBooksSpec.addBooksSpecResponse;
import static specs.EmptyLibrarySpec.emptyLibrarySpecRequest;
import static specs.EmptyLibrarySpec.emptyLibrarySpecResponse;
import static specs.LoginSpec.loginSpecRequest;
import static specs.LoginSpec.loginSpecResponse;
import static specs.ProfileSpec.profileSpecRequest;
import static specs.ProfileSpec.profileSpecResponse;

public class StepsTest {

    public LoginResponseModel loginResponse;

    @Step("Генерация авторизационного токена пользователя")
    public StepsTest generateToken(LoginBodyModel authData) {
        given(loginSpecRequest)
                .body(authData)
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .spec(loginSpecResponse);
        return this;
    }

    @Step("Авторизация пользователя на портале")
    public StepsTest login(LoginBodyModel authData) {
        loginResponse = given(loginSpecRequest)
                .body(authData)
                .when()
                .post("/Account/v1/login")
                .then()
                .spec(loginSpecResponse)
                .extract().as(LoginResponseModel.class);
        return this;
    }

    @Step("Очистка библиотеки пользователя")
    public StepsTest cleanLibrary() {
        given(emptyLibrarySpecRequest)
                .header("authorization", "Bearer " + loginResponse.getToken())
                .when()
                .delete("/BookStore/v1/Books?UserId=" + loginResponse.getUserId())
                .then()
                .spec(emptyLibrarySpecResponse);

        return this;
    }

    @Step("Добавление книги в библиотеку")
    public StepsTest addTestBook() {
        AddingBooksToProfileModel buyBooks = new AddingBooksToProfileModel(loginResponse.getUserId(),
                new BookIsbnModel[]{new BookIsbnModel("9781449325862")});

        given(addBooksSpecRequest)
                .header("authorization", "Bearer " + loginResponse.getToken())
                .when()
                .body(buyBooks)
                .post("/BookStore/v1/Books")
                .then()
                .spec(addBooksSpecResponse);

        return this;
    }

    @Step("Вход на портал через UI")
    public StepsTest openProfilePageUI() {
        open("/images/Toolsqa.jpg");
        getWebDriver().manage().addCookie(new Cookie("token", loginResponse.getToken()));
        getWebDriver().manage().addCookie(new Cookie("userID", loginResponse.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("expires", loginResponse.getExpires()));
        open("/profile");

        return this;
    }

    @Step("Удаление книги из профиля пользователя")
    public StepsTest deleteBookUI() {
        $("#delete-record-undefined").click();
        $("#closeSmallModal-ok").click();

        Selenide.dismiss();

        return this;
    }

    @Step("Проверка удаления книги из библиотеки пользователя")
    public StepsTest libraryMustBeEmptyCheck() {
        ProfileResponseModel profileResponse =
                given(profileSpecRequest)
                        .header("authorization", "Bearer " + loginResponse.getToken())
                        .when()
                        .get("/Account/v1/User/" + loginResponse.getUserId())
                        .then()
                        .spec(profileSpecResponse)
                        .extract().as(ProfileResponseModel.class);

        assertThat(profileResponse.getBooks()).isEmpty();


        return this;
    }


}
