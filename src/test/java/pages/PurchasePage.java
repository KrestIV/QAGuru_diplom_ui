package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;

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
            tareaOrderComment = $("textarea[name=\"data[new][order_comments]\"]");

//    $("input[name=\"data[new][index]\"]").shouldBe(visible);
//    $("input[name=\"data[new][region]\"]").shouldBe(visible);
//    $("input[name=\"data[new][city]\"]").shouldBe(visible);
//    $("input[name=\"data[new][street]\"]").shouldBe(visible);
//    $("input[name=\"data[new][house]\"]").shouldBe(visible);
//    $("input[name=\"data[new][flat]\"]").shouldBe(visible);
//    $("textarea[name=\"data[new][order_comments]\"]").shouldBe(visible);

    @Step("Открыть страницу заказа авторизованным пользователем")
    public PurchasePage openPurchasePageWithAuthorizedUser(String[] cookies) {
        open("/images/dsgn/menu_cats_pic.png");
        getWebDriver().manage().addCookie(new Cookie("PHPSESSID", cookies[0]));
        getWebDriver().manage().addCookie(new Cookie("stat_id", cookies[1]));
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
        tareaOrderComment.shouldBe(visible);
    }
}
