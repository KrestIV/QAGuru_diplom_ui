package ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import models.AuthDataModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;

public class UIBaseTest {

    private static final Log log = LogFactory.getLog(UIBaseTest.class);

    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "http://kakadu39.ru";
        RestAssured.baseURI = "http://kakadu39.ru";

        Configuration.pageLoadStrategy = "eager";

        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
//        Configuration.holdBrowserOpen = true;
        //Configuration.remote = getServer();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;
    }

    @BeforeEach
    void beforeEach() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void afterEach() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        Selenide.closeWebDriver();
    }

    static String getServer() {
        String login = System.getProperty("receiveCookies");
        String pw = System.getProperty("pw");
        String server = System.getProperty("server");

        if (login != null && pw != null && server != null)
            return "https://" + login + ":" + pw + "@" + server + "/wd/hub";
        else
            return null;
    }

    public AuthDataModel getAuthInfo(){
        String login = "tegir_st";
        String pw = "RJSPFPyL8hLgekC";

        Map<String, String> authData = new HashMap<>();
        authData.put("login",login);
        authData.put("password",pw);
        authData.put("from_page","/");

        AuthDataModel authFormData = new AuthDataModel();
        authFormData.setAuthFormData(authData);

        return authFormData;
    }
}