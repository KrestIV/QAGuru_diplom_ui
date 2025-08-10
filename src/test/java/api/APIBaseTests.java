package api;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class APIBaseTests {

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/pet";

        //Configuration.remote = getServer();
    }

    @BeforeEach
    public void enableAllureListener() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

}
