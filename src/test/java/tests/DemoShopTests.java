package tests;

import com.codeborne.selenide.Selenide;
import models.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.EmptyLibrarySpec.emptyLibrarySpecRequest;
import static specs.EmptyLibrarySpec.emptyLibrarySpecResponse;
import static specs.LoginSpec.loginSpecRequest;
import static specs.LoginSpec.loginSpecResponse;
import static specs.AddBooksSpec.addBooksSpecRequest;
import static specs.AddBooksSpec.addBooksSpecResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static specs.ProfileSpec.profileSpecRequest;
import static specs.ProfileSpec.profileSpecResponse;

public class DemoShopTests extends TestBase {

    @Test
    public void deleteItemFromCartTest() {

        LoginBodyModel authData = new LoginBodyModel();
        authData.setUserName("krestsovTestUser");
        authData.setPassword("tNj8VqCg!DU8UpN");
        step("Генерация авторизационного токена пользователя", () ->
                given(loginSpecRequest)
                        .body(authData)
                        .when()
                        .post("/Account/v1/GenerateToken")
                        .then()
                        .spec(loginSpecResponse));


        LoginResponseModel loginResponse =
                step("Авторизация пользователя на портале", () ->
                        given(loginSpecRequest)
                                .body(authData)
                                .when()
                                .post("/Account/v1/login")
                                .then()
                                .spec(loginSpecResponse)
                                .extract().as(LoginResponseModel.class));
        String userToken = loginResponse.getToken();


        step("Очистка библиотеки пользователя", () ->
                given(emptyLibrarySpecRequest)
                        .header("authorization", "Bearer " + userToken)
                        .when()
                        .delete("/BookStore/v1/Books?UserId=" + loginResponse.getUserId())
                        .then()
                        .spec(emptyLibrarySpecResponse));


        step("Добавление книги в библиотеку пользователя", () -> {
            AddingBooksToProfileModel buyBooks = new AddingBooksToProfileModel();
            buyBooks.setUserId(loginResponse.getUserId());

            BookIsbnModel[] booksToBuy = new BookIsbnModel[1];
            booksToBuy[0] = new BookIsbnModel();
            booksToBuy[0].setIsbn("9781449325862");

            buyBooks.setCollectionOfIsbns(booksToBuy);

            given(addBooksSpecRequest)
                    .header("authorization", "Bearer " + userToken)
                    .when()
                    .body(buyBooks)
                    .post("/BookStore/v1/Books")
                    .then()
                    .spec(addBooksSpecResponse);
        });


        step("Вход на портал через UI",() ->{
            open("/images/Toolsqa.jpg");
            getWebDriver().manage().addCookie(new Cookie("token", userToken));
            getWebDriver().manage().addCookie(new Cookie("userID", loginResponse.getUserId()));
            getWebDriver().manage().addCookie(new Cookie("expires", loginResponse.getExpires()));
            open("/profile");});

        step("Удаление книги из профиля пользователя", ()->{
            $("#delete-record-undefined").click();
            $("#closeSmallModal-ok").click();

        Selenide.dismiss();});


        step("Проверка удаления книги из библиотеки пользователя", () ->
        {
            ProfileResponseModel profileResponse =
                    given(profileSpecRequest)
                            .header("authorization", "Bearer " + userToken)
                            .when()
                            .get("/Account/v1/User/" + loginResponse.getUserId())
                            .then()
                            .spec(profileSpecResponse)
                            .extract().as(ProfileResponseModel.class);
            assertThat(profileResponse.getBooks()).isEmpty();
        });

    }
}
