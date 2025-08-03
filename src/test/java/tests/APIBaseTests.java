package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;

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
}
