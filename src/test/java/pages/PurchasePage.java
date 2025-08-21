package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static pages.OpenPageLoadCookiesComponent.openPageLoadCookies;

public class PurchasePage {

    private final SelenideElement inputPostIndex = $("input[name=\"data[new][index]\"]"),
            inputRegion = $("input[name=\"data[new][region]\"]"),
            inputCity = $("input[name=\"data[new][city]\"]"),
            inputStreet = $("input[name=\"data[new][street]\"]"),
            inputBuilding = $("input[name=\"data[new][house]\"]"),
            inputApartment = $("input[name=\"data[new][flat]\"]"),
            tAreaOrderComment = $("textarea[name=\"data[new][order_comments]\"]");

    @Step("Открыть страницу заказа авторизованным пользователем")
    public PurchasePage openPurchasePageWithAuthorizedUser() {

        openPageLoadCookies();
        open("/emarket/purchase/?");

        return this;
    }

    @Step("Проверить отображение формы заказа")
    public void checkPurchaseForm() {
        inputPostIndex.shouldBe(visible);
        inputRegion.shouldBe(visible);
        inputCity.shouldBe(visible);
        inputStreet.shouldBe(visible);
        inputBuilding.shouldBe(visible);
        inputApartment.shouldBe(visible);
        tAreaOrderComment.shouldBe(visible);
    }
}
