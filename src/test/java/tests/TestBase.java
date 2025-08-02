package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import models.LoginBodyModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

public class TestBase {

    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "http://kakadu39.ru";
        RestAssured.baseURI = "http://kakadu39.ru";

        Configuration.pageLoadStrategy = "eager";

        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.holdBrowserOpen = true;
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
        String login = System.getProperty("login");
        String pw = System.getProperty("pw");
        String server = System.getProperty("server");

        if (login != null && pw != null && server != null)
            return "https://" + login + ":" + pw + "@" + server + "/wd/hub";
        else
            return null;
    }

    static LoginBodyModel getAuthData() {
        String login = System.getProperty("shoplogin");
        String pw = System.getProperty("shoppw");

        return new LoginBodyModel(login, pw, "/");
    }
}