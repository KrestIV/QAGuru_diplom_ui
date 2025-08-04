package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class DogFoodPage {

    private final SelenideElement buttonAddItemToCart = $("a[href=\"/emarket/basket/put/element/3285/\"]");

    @Step("Открыть страницу авторизованным пользователем")
    public DogFoodPage openPageWithAuthorizedUser(String[] cookies) {
        open("/images/dsgn/menu_cats_pic.png");
        getWebDriver().manage().addCookie(new Cookie("PHPSESSID", cookies[0]));
        getWebDriver().manage().addCookie(new Cookie("stat_id", cookies[1]));
        open("/razdely_i_tovary/molina_krabov_palochki_s_kur_dsobak_80g/korma_dlya_sobak/");

        return this;
    }

    @Step("Добавить товар в корзину")
    public DogFoodPage addItemToCart() {
        buttonAddItemToCart.click();

        return this;
    }
}
