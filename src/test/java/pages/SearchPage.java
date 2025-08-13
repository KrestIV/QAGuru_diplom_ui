package pages;

import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static org.assertj.core.api.Assertions.assertThat;

public class SearchPage {

    private final ElementsCollection areaSearchResultLines = $("div.grey_border_block_center").$$("p");

    @Step("Проверить результаты успешного поиска")
    public SearchPage successfulSearchResultsCheck() {

        assertThat(areaSearchResultLines.stream().count()).isGreaterThan(2L);

        return this;
    }
}
