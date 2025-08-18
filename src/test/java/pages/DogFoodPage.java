package pages;

import com.codeborne.selenide.SelenideElement;
import helpers.TestDataStorage;
import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;

import java.util.Map;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class DogFoodPage {

    private final SelenideElement buttonAddItemToCart = $("a[href=\"/emarket/basket/put/element/" + TestDataStorage.getItemCartPrimaryId() + "/\"]");

    @Step("Открыть страницу авторизованным пользователем")
    public DogFoodPage openPageWithAuthorizedUser(Map<String, String> cookies) {
        open("/images/dsgn/menu_cats_pic.png");
        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            getWebDriver().manage().addCookie(new Cookie(key, value));
        }

        open("/razdely_i_tovary/molina_krabov_palochki_s_kur_dsobak_80g/korma_dlya_sobak/");

        return this;
    }

    @Step("Добавить товар в корзину")
    public DogFoodPage addItemToCart() {
        buttonAddItemToCart.click();

        return this;
    }
}
