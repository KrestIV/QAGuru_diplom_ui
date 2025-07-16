package tests;

import io.qameta.allure.Step;
import models.AddingBooksToProfileModel;
import models.BookIsbnModel;
import models.LoginResponseModel;

import static io.restassured.RestAssured.given;
import static specs.RequestSpec.*;
import static specs.RequestSpec.responseSpec;

public class BookStoreTests {

    @Step("Очистка библиотеки пользователя")
    public BookStoreTests cleanLibrary(LoginResponseModel loginResponse) {
        given(requestNoContentSpec)
                .header("authorization", "Bearer " + loginResponse.getToken())
                .when()
                .delete("/BookStore/v1/Books?UserId=" + loginResponse.getUserId())
                .then()
                .spec(responseSpec(204));

        return this;
    }

    @Step("Добавление книги в библиотеку")
    public BookStoreTests addTestBook(LoginResponseModel loginResponse) {
        AddingBooksToProfileModel buyBooks = new AddingBooksToProfileModel(loginResponse.getUserId(),
                new BookIsbnModel[]{new BookIsbnModel("9781449325862")});

        given(requestWithContentSpec)
                .header("authorization", "Bearer " + loginResponse.getToken())
                .when()
                .body(buyBooks)
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpec(201));

        return this;
    }
}
