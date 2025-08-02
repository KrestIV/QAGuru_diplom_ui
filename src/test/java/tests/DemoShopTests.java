package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.conditions.Text;
import io.restassured.path.xml.XmlPath;
import models.LoginBodyModel;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import pages.AccountPage;
import steps.AccountApiSteps;
import steps.BookStoreApiSteps;
import io.restassured.response.Response;
import io.restassured.http.Cookies;

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
//        AccountPage accountPage = new AccountPage();
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

        open("/");
        $("input#login").setValue("tegir_st");
        $("input#password").setValue("RJSPFPyL8hLgekC");
        $("input.log_in_btn").click();

        $("div.login_form").shouldHave(text("Добро пожаловать"));
    }

    @Test
    public void addingItemToCartMustAddItemToCartTest(){

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

        open("/images/dsgn/menu_cats_pic.png");
        getWebDriver().manage().addCookie(new Cookie("PHPSESSID", phpSessId));
        getWebDriver().manage().addCookie(new Cookie("stat_id", statId));

        open("/razdely_i_tovary/molina_krabov_palochki_s_kur_dsobak_80g/korma_dlya_sobak/");

        $("a[href=\"/emarket/basket/put/element/3285/\"]").click();

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
    public void deletingItemFromCartMustEmptyCartTest(){
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
        open("/images/dsgn/menu_cats_pic.png");
        getWebDriver().manage().addCookie(new Cookie("PHPSESSID", phpSessId));
        getWebDriver().manage().addCookie(new Cookie("stat_id", statId));

        open("/emarket/cart/");

        $("a[title=\"Удалить\"]").click();

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

        //UI open checkout page
        open("/images/dsgn/menu_cats_pic.png");
        getWebDriver().manage().addCookie(new Cookie("PHPSESSID", phpSessId));
        getWebDriver().manage().addCookie(new Cookie("stat_id", statId));

        open("/emarket/purchase/?");

        //check checkout page
        $("input[name=\"data[new][index]\"]").shouldBe(visible);
        $("input[name=\"data[new][region]\"]").shouldBe(visible);
        $("input[name=\"data[new][city]\"]").shouldBe(visible);
        $("input[name=\"data[new][street]\"]").shouldBe(visible);
        $("input[name=\"data[new][house]\"]").shouldBe(visible);
        $("input[name=\"data[new][flat]\"]").shouldBe(visible);
        $("textarea[name=\"data[new][order_comments]\"]").shouldBe(visible);

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

        //open main page
        open("/images/dsgn/menu_cats_pic.png");
        getWebDriver().manage().addCookie(new Cookie("PHPSESSID", phpSessId));
        getWebDriver().manage().addCookie(new Cookie("stat_id", statId));

        open("/");

        //search
        $("input[name=\"search_string\"]").setValue("фитомины").submit();

        //check result
        $("div.grey_border_block_center").shouldHave(text("страниц"));
    }


    //API tests
    @Test
    public void authorizeBack() {
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
                .post("http://kakadu39.ru/users/login_do/");


        response.then()
                .spec(responseSpec(301));

        Cookies cookies = response.getDetailedCookies();
        String phpSessId = cookies.getValue("PHPSESSID");
        String statId = cookies.getValue("stat_id");


        response = given(requestNoContentSpec)
                .cookies("PHPSESSID",phpSessId)
                .cookies("stat_id",statId)
                .when()
                .get("http://kakadu39.ru/");

        response.then()
                .spec(responseSpec(200));
    }
}
