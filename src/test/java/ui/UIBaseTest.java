package ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.LaunchConfig;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import models.AuthDataModel;
import org.aeonbits.owner.ConfigFactory;
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
    static LaunchConfig config = ConfigFactory.create(LaunchConfig.class, System.getProperties());

    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "http://kakadu39.ru";
        RestAssured.baseURI = "http://kakadu39.ru";

        Configuration.pageLoadStrategy = "eager";

        Configuration.browser = config.getBrowserName();
        Configuration.browserVersion = config.getBrowserVersion();
        Configuration.browserSize = config.getBrowserSize();
        Configuration.remote = config.getServerAddress();

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

    public AuthDataModel getAuthInfo(){
        String login = config.getShopLogin();
        String pw = config.getShopPassword();

        Map<String, String> authData = new HashMap<>();
        authData.put("login",login);
        authData.put("password",pw);
        authData.put("from_page","/");

        AuthDataModel authFormData = new AuthDataModel();
        authFormData.setAuthFormData(authData);

        return authFormData;
    }
}