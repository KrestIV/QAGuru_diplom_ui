package pages;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import models.LoginResponseModel;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class AccountPage {

    @Step("Вход на портал через UI")
    public AccountPage openProfilePageUI(LoginResponseModel loginResponse) {
        open("/images/Toolsqa.jpg");
        getWebDriver().manage().addCookie(new Cookie("token", loginResponse.getToken()));
        getWebDriver().manage().addCookie(new Cookie("userID", loginResponse.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("expires", loginResponse.getExpires()));
        open("/profile");

        return this;
    }

    @Step("Удаление книги из профиля пользователя")
    public AccountPage deleteBookUI() {
        $("#delete-record-undefined").click();
        $("#closeSmallModal-ok").click();

        Selenide.dismiss();

        return this;
    }
}
