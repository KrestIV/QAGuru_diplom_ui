package pages;

import helpers.CookieStorage;
import org.openqa.selenium.Cookie;

import java.util.Map;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class OpenPageLoadCookiesComponent {
    public static void openPageLoadCookies() {
        open(CookieStorage.getUrlPicture());
        for (Map.Entry<String, String> entry : CookieStorage.getCookies().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            getWebDriver().manage().addCookie(new Cookie(key, value));
        }
    }
}
