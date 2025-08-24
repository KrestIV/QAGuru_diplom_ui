package pages;

import storages.CookieStorage;
import org.openqa.selenium.Cookie;
import storages.OpenPicUrlStorage;

import java.util.Map;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class OpenPageLoadCookiesComponent {
    public static void openPageLoadCookies() {
        open(OpenPicUrlStorage.getUrlPicture());
        for (Map.Entry<String, String> entry : CookieStorage.getCookies().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            getWebDriver().manage().addCookie(new Cookie(key, value));
        }
    }
}
