package uitests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.LaunchConfig;
import helpers.Attach;
import helpers.AuthDataStorage;
import helpers.CookieStorage;
import helpers.TestDataStorage;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import models.AuthDataModel;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

public class UIBaseTest {

    @BeforeAll
    static void beforeAll() {
        LaunchConfig config = ConfigFactory.create(LaunchConfig.class, System.getProperties());

        Configuration.baseUrl = config.getBaseUrl();
        RestAssured.baseURI = config.getBaseUrl();

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


        AuthDataModel authDataModel = new AuthDataModel(Map.of(
                "login", config.getShopLogin(),
                "password", config.getShopPassword(),
                "from_page", "/"
        ));

        AuthDataStorage.setAuthDataContainer(authDataModel);

        TestDataStorage.setItemCartPrimaryId(config.getItemCartPrimaryId());
        TestDataStorage.setItemCartPrimaryDescription(config.getItemCartPrimaryDescription());
        TestDataStorage.setItemCartSecondaryId(config.getItemCartSecondaryId());
        TestDataStorage.setSearchWord(config.getSearchWord());

        CookieStorage.setUrlPicture(config.getUrlPicture());
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
}