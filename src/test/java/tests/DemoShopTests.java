package tests;

import models.LoginBodyModel;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import io.restassured.response.Response;
import io.restassured.http.Cookies;
import pages.*;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.RequestSpec.*;

public class DemoShopTests extends TestBase {

//    @Test
//    public void deleteItemFromCartTest() {
//        AccountApiSteps account = new AccountApiSteps();
//        BookStoreApiSteps bookStore = new BookStoreApiSteps();
//        MainPage accountPage = new MainPage();
//
//
//        account.generateToken(getAuthData())
//                .login(getAuthData());
//
//        bookStore.cleanLibrary(account.loginResponse)
//                .addTestBook(account.loginResponse);
//
//        accountPage.openProfilePageUI(account.loginResponse)
//                .deleteBookUI();
//
//        account.libraryMustBeEmptyCheck();
//
//    }

    //UI Tests
    @Test
    public void loginWithCorrectCredentialsMustGreetUserTest() {

        MainPage mainPage = new MainPage();

        mainPage.openMainPage()
                .login(getAuthData())
                .checkLogin();

    }

    @Test
    public void addingItemToCartMustAddItemToCartTest(){

        DogFoodPage dogFoodPage = new DogFoodPage();

        //authorize
        String login = "tegir_st";
        String pw = "RJSPFPyL8hLgekC";
        LoginBodyModel authData = new LoginBodyModel(login, pw, "/");
        Response response = given(requestWithContentSpec)
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                //.body(authData)
                .formParam("login", login)
                .formParam("password", pw)
                .formParam("from_page", "/")
                .when()
                .post("/users/login_do/");


        response.then()
                .spec(responseSpec(301));

        Cookies cookies = response.getDetailedCookies();
        String phpSessId = cookies.getValue("PHPSESSID");
        String statId = cookies.getValue("stat_id");

        //clean cart
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/emarket/basket/remove_all/");

        response.then()
                .spec(responseSpec(200));
        //UI add item

        dogFoodPage.openPageWithAuthorizedUser(new String[]{phpSessId,statId})
                .addItemToCart();

        //check cart
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/emarket/cart/");

        response.then()
                .spec(responseSpec(200));

        String html = response.getBody().asString();
        assertThat(html)
                .contains("Brit Fresh Chicken");

        //clean cart
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/emarket/basket/remove_all/");

        response.then()
                .spec(responseSpec(200));

    }

    @Test
    public void addingTwoItemsToCartMustDisplayNumberOfItemsInCartTest(){

        CartPage cartPage = new CartPage();

        //authorize
        String login = "tegir_st";
        String pw = "RJSPFPyL8hLgekC";
        LoginBodyModel authData = new LoginBodyModel(login, pw, "/");
        Response response = given(requestWithContentSpec)
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                //.body(authData)
                .formParam("login", login)
                .formParam("password", pw)
                .formParam("from_page", "/")
                .when()
                .post("/users/login_do/");


        response.then()
                .spec(responseSpec(301));

        Cookies cookies = response.getDetailedCookies();
        String phpSessId = cookies.getValue("PHPSESSID");
        String statId = cookies.getValue("stat_id");

        //clean cart
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/emarket/basket/remove_all/");

        response.then()
                .spec(responseSpec(200));

        //add item
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/emarket/basket/put/element/3158/");

        response.then()
                .spec(responseSpec(200));

        //add item
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/emarket/basket/put/element/3158/");

        response.then()
                .spec(responseSpec(200));

        //UI open cart
//        open("/images/dsgn/menu_cats_pic.png");
//        getWebDriver().manage().addCookie(new Cookie("PHPSESSID", phpSessId));
//        getWebDriver().manage().addCookie(new Cookie("stat_id", statId));
//
//        open("/emarket/cart/");

        cartPage.openCartPageWithAuthorizedUser(new String[]{phpSessId,statId})
                .checkFirstItemQuantity();

        //clean cart
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/emarket/basket/remove_all/");

        response.then()
                .spec(responseSpec(200));
    }

    @Test
    public void deletingItemFromCartMustEmptyCartTest(){
        CartPage cartPage = new CartPage();

        //authorize
        String login = "tegir_st";
        String pw = "RJSPFPyL8hLgekC";
        LoginBodyModel authData = new LoginBodyModel(login, pw, "/");
        Response response = given(requestWithContentSpec)
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                //.body(authData)
                .formParam("login", login)
                .formParam("password", pw)
                .formParam("from_page", "/")
                .when()
                .post("/users/login_do/");


        response.then()
                .spec(responseSpec(301));

        Cookies cookies = response.getDetailedCookies();
        String phpSessId = cookies.getValue("PHPSESSID");
        String statId = cookies.getValue("stat_id");

        //clean cart
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/emarket/basket/remove_all/");

        response.then()
                .spec(responseSpec(200));

        //add item
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/emarket/basket/put/element/3158/");

        response.then()
                .spec(responseSpec(200));
        //UI delete item
//        open("/images/dsgn/menu_cats_pic.png");
//        getWebDriver().manage().addCookie(new Cookie("PHPSESSID", phpSessId));
//        getWebDriver().manage().addCookie(new Cookie("stat_id", statId));
//
//        open("/emarket/cart/");
//
//        $("a[title=\"Удалить\"]").click();

        cartPage.openCartPageWithAuthorizedUser(new String[]{phpSessId,statId})
                .deleteFirstItem();

        //check cart
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/emarket/cart/");

        response.then()
                .spec(responseSpec(200));

        String html = response.getBody().asString();
        assertThat(html)
                .contains("Корзина пуста");

    }

    @Test
    public void clearCartMustEmptyCartTest(){
        CartPage cartPage = new CartPage();

        //authorize
        String login = "tegir_st";
        String pw = "RJSPFPyL8hLgekC";
        LoginBodyModel authData = new LoginBodyModel(login, pw, "/");
        Response response = given(requestWithContentSpec)
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                //.body(authData)
                .formParam("login", login)
                .formParam("password", pw)
                .formParam("from_page", "/")
                .when()
                .post("/users/login_do/");


        response.then()
                .spec(responseSpec(301));

        Cookies cookies = response.getDetailedCookies();
        String phpSessId = cookies.getValue("PHPSESSID");
        String statId = cookies.getValue("stat_id");

        //clean cart
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/emarket/basket/remove_all/");

        response.then()
                .spec(responseSpec(200));

        //add first item
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/emarket/basket/put/element/3158/");

        response.then()
                .spec(responseSpec(200));

        //add second item
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/emarket/basket/put/element/721468/");

        response.then()
                .spec(responseSpec(200));

        //UI clear cart
//        open("/images/dsgn/menu_cats_pic.png");
//        getWebDriver().manage().addCookie(new Cookie("PHPSESSID", phpSessId));
//        getWebDriver().manage().addCookie(new Cookie("stat_id", statId));
//
//        open("/emarket/cart/");
//
//        $("a[href=\"/emarket/basket/remove_all/\"]").click();

        cartPage.openCartPageWithAuthorizedUser(new String[]{phpSessId,statId})
                .clearCart();

        //check cart
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/emarket/cart/");

        response.then()
                .spec(responseSpec(200));

        String html = response.getBody().asString();
        assertThat(html)
                .contains("Корзина пуста");

    }

    @Test
    public void openingPurchasePageMustShowPurchaseFormTest(){
        PurchasePage purchasePage = new PurchasePage();

        //authorize
        String login = "tegir_st";
        String pw = "RJSPFPyL8hLgekC";
        LoginBodyModel authData = new LoginBodyModel(login, pw, "/");
        Response response = given(requestWithContentSpec)
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                //.body(authData)
                .formParam("login", login)
                .formParam("password", pw)
                .formParam("from_page", "/")
                .when()
                .post("/users/login_do/");


        response.then()
                .spec(responseSpec(301));

        Cookies cookies = response.getDetailedCookies();
        String phpSessId = cookies.getValue("PHPSESSID");
        String statId = cookies.getValue("stat_id");

        //clean cart
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/emarket/basket/remove_all/");

        response.then()
                .spec(responseSpec(200));

        //add item
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/emarket/basket/put/element/3158/");

        response.then()
                .spec(responseSpec(200));

//        //UI open checkout page
//        open("/images/dsgn/menu_cats_pic.png");
//        getWebDriver().manage().addCookie(new Cookie("PHPSESSID", phpSessId));
//        getWebDriver().manage().addCookie(new Cookie("stat_id", statId));
//
//        open("/emarket/purchase/?");
//
//        //check checkout page
//        $("input[name=\"data[new][index]\"]").shouldBe(visible);
//        $("input[name=\"data[new][region]\"]").shouldBe(visible);
//        $("input[name=\"data[new][city]\"]").shouldBe(visible);
//        $("input[name=\"data[new][street]\"]").shouldBe(visible);
//        $("input[name=\"data[new][house]\"]").shouldBe(visible);
//        $("input[name=\"data[new][flat]\"]").shouldBe(visible);
//        $("textarea[name=\"data[new][order_comments]\"]").shouldBe(visible);

        purchasePage.openPurchasePageWithAuthorizedUser(new String[]{phpSessId,statId})
                .checkPurchaseForm();

        //clean cart
        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("/emarket/basket/remove_all/");

        response.then()
                .spec(responseSpec(200));
    }

    @Test
    public void searchItemMustShowListOfItemsTest(){
        MainPage mainPage = new MainPage();
        SearchPage searchPage = new SearchPage();

        mainPage.openMainPage()
                .search("фитомины");
        searchPage.successfulSearchResultsCheck();
    }
}
