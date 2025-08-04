package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class CartPage {

    private final SelenideElement orderTableFirstRow = $("table#order_block").$("tbody").$("tr"),
            buttonFirstItemDelete = $("a[title=\"Удалить\"]"),
            buttonDeleteAll = $("a[href=\"/emarket/basket/remove_all/\"]");

    @Step("Открыть корзину авторизованным пользователем")
    public CartPage openCartPageWithAuthorizedUser(String[] cookies) {
        open("/images/dsgn/menu_cats_pic.png");
        getWebDriver().manage().addCookie(new Cookie("PHPSESSID", cookies[0]));
        getWebDriver().manage().addCookie(new Cookie("stat_id", cookies[1]));
        open("/emarket/cart/");

        return this;
    }

    @Step("Удалить первый товар в списке")
    public CartPage deleteFirstItem() {

        buttonFirstItemDelete.click();

        return this;
    }

    @Step("Очистить корзину")
    public CartPage clearCart() {

        buttonDeleteAll.click();

        return this;
    }

    @Step("Проверить количество добавленного в корзину товара")
    public CartPage checkFirstItemQuantity() {

        orderTableFirstRow.$$("td").findBy(text("2")).shouldBe();

        return this;
    }
}
