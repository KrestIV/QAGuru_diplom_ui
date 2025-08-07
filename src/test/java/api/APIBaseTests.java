package api;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.Map;

public class APIBaseTests {

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "http://kakadu39.ru";

        //Configuration.remote = getServer();
    }

    @BeforeEach
    public void enableAllureListener() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    public Map<String, String> getAuthInfo(String additionToPassword){
        String login = "tegir_st";
        String pw = "RJSPFPyL8hLgekC" + additionToPassword;

        Map<String, String> authFormData = new HashMap<>();
        authFormData.put("login",login);
        authFormData.put("password",pw);
        authFormData.put("from_page","/");

        return authFormData;
    }
}
