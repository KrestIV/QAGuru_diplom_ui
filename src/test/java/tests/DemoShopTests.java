package tests;

import models.*;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;


public class DemoShopTests {

    @Test
    public void deleteItemFromCartTest (){
        //login
        LoginCorrectBodyModel authData = new LoginCorrectBodyModel();
        authData.setUserName("krestsovTestUser");
        authData.setPassword("tNj8VqCg!DU8UpN");

        LoginResponseModel loginResponse = given()
                .log().all()
                .contentType(JSON)
                .when()
                .body(authData)
                .post("https://demoqa.com/Account/v1/Login")
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(LoginResponseModel.class);
        String userToken = loginResponse.getToken();

        //empty library

        given()
                        .header("authorization","Bearer " + userToken)
                        .log().all()
                        .when()
                        .delete("https://demoqa.com/BookStore/v1/Books?UserId=" + loginResponse.getUserId())
                        .then()
                        .log().all()
                        .statusCode(204);

        //add Items to cart

        AddingBooksToProfileModel buyBooks = new AddingBooksToProfileModel();
        buyBooks.setUserId(loginResponse.getUserId());

        BookIsbnModel[] booksToBuy = new BookIsbnModel[1];
        booksToBuy[0] = new BookIsbnModel();
        booksToBuy[0].setIsbn("9781449325862");

        buyBooks.setCollectionOfIsbns(booksToBuy);

        given()
                .log().all()
                .header("authorization","Bearer " + userToken)
                .contentType(JSON)
                .when()
                .body(buyBooks)
                .post("https://demoqa.com/BookStore/v1/Books")
                .then()
                .log().all()
                .statusCode(201);

        //delete item from cart by UI


        //check for result
        ProfileResponseModel profileResponse =
                given()
                        .header("authorization","Bearer " + userToken)
                        .log().all()
                        .when()
                        .get("https://demoqa.com/Account/v1/User/" + loginResponse.getUserId())
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().as(ProfileResponseModel.class);
        System.out.println(profileResponse.getBooks().length);
    }
}
