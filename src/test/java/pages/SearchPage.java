package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class SearchPage {

    private final SelenideElement areaSearchResult = $("div.grey_border_block_center");

    @Step("Проверить результаты успешного поиска")
    public SearchPage successfulSearchResultsCheck() {

        areaSearchResult.shouldHave(text("страниц"));

        return this;
    }
}
