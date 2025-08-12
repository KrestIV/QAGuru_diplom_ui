package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;

import java.util.Map;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class PurchasePage {

    private final SelenideElement inputPostIndex = $("input[name=\"data[new][index]\"]"),
            inputRegion = $("input[name=\"data[new][region]\"]"),
            inputCity = $("input[name=\"data[new][city]\"]"),
            inputStreet = $("input[name=\"data[new][street]\"]"),
            inputBuilding = $("input[name=\"data[new][house]\"]"),
            inputApartment = $("input[name=\"data[new][flat]\"]"),
            tAreaOrderComment = $("textarea[name=\"data[new][order_comments]\"]");

    @Step("Открыть страницу заказа авторизованным пользователем")
    public PurchasePage openPurchasePageWithAuthorizedUser(Map<String, String> cookies) {
        open("/images/dsgn/menu_cats_pic.png");
        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            getWebDriver().manage().addCookie(new Cookie(key, value));
        }
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
