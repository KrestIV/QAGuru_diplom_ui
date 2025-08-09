package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import models.AuthDataModel;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class MainPage {

    private final SelenideElement inputLogin = $("input#receiveCookies"),
            inputPassword = $("input#password"),
            buttonSubmit = $("input.log_in_btn"),
            formLogin = $("div.login_form"),
            inputSearch = $("input[name=\"search_string\"]");

    @Step("Открыть главную страницу")
    public MainPage openMainPage() {
        open("/");
        return this;
    }

    @Step("Авторизоваться пользователем")
    public MainPage login(AuthDataModel loginInfo){
        inputLogin.setValue(loginInfo.getAuthFormData().get("receiveCookies"));
        inputPassword.setValue(loginInfo.getAuthFormData().get("password"));
        buttonSubmit.click();
        return this;
    }

    @Step("Проверить успешность авторизации")
    public void checkLogin(){
        formLogin.shouldHave(text("Добро пожаловать"));
    }



    @Step("Поиск товара")
    public MainPage search(String searchString) {

        inputSearch.setValue(searchString).submit();

        return this;
    }
}
