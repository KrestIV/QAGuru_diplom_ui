package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static pages.OpenPageLoadCookiesComponent.openPageLoadCookies;

public class CartPage {

    private final SelenideElement orderTableFirstRow = $("table#order_block").$("tbody").$("tr"),
            buttonFirstItemDelete = $("a[title=\"Удалить\"]"),
            buttonDeleteAll = $("a[href=\"/emarket/basket/remove_all/\"]");

    @Step("Открыть корзину авторизованным пользователем")
    public CartPage openCartPageWithAuthorizedUser() {

        openPageLoadCookies();
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
    public CartPage checkFirstItemQuantity(Integer quantity) {

        orderTableFirstRow.$$("td").get(1).shouldHave(text(quantity.toString()));

        return this;
    }
}
