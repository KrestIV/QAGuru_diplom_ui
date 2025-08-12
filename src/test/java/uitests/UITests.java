package uitests;

import helpers.CookieStorage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.*;
import uiapisteps.CartAPISteps;
import uiapisteps.LoginAPISteps;

//@Tag("UITests")
@Tag("FullTest")
public class UITests extends UIBaseTest {

    MainPage mainPage = new MainPage();
    SearchPage searchPage = new SearchPage();
    PurchasePage purchasePage = new PurchasePage();
    CartPage cartPage = new CartPage();
    DogFoodPage dogFoodPage = new DogFoodPage();
    LoginAPISteps apiClient = new LoginAPISteps();
    CartAPISteps apiCart = new CartAPISteps();

    //UI Tests
    @Test
    @Tag("UITests")
    public void loginWithCorrectCredentialsMustGreetUserTest() {

        MainPage mainPage = new MainPage();

        mainPage.openMainPage()
                .login(getAuthInfo())
                .checkLogin();
    }

    @Test
    public void addingItemToCartMustAddItemToCartTest() {

        apiClient.receiveCookies(getAuthInfo());

        apiCart.prepareCart();

        dogFoodPage.openPageWithAuthorizedUser(CookieStorage.getCookies())
                .addItemToCart();

        apiCart.checkCart("Brit Fresh Chicken");
    }

    @Test
    public void addingTwoItemsToCartMustDisplayNumberOfItemsInCartTest() {

        apiClient.receiveCookies(getAuthInfo());

        apiCart.prepareCart()
                .putItemToCart("3158")
                .putItemToCart("3158");

        cartPage.openCartPageWithAuthorizedUser(CookieStorage.getCookies())
                .checkFirstItemQuantity();

        apiCart.prepareCart();
    }

    @Test
    public void deletingItemFromCartMustEmptyCartTest() {

        apiClient.receiveCookies(getAuthInfo());

        apiCart.prepareCart()
                .putItemToCart("3158");

        cartPage.openCartPageWithAuthorizedUser(CookieStorage.getCookies())
                .deleteFirstItem();

        apiCart.checkCart("Корзина пуста");
    }

    @Test
    public void clearCartMustEmptyCartTest() {

        apiClient.receiveCookies(getAuthInfo());

        apiCart.prepareCart()
                .putItemToCart("3158")
                .putItemToCart("721468");

        cartPage.openCartPageWithAuthorizedUser(CookieStorage.getCookies())
                .clearCart();

        apiCart.checkCart("Корзина пуста");
    }

    @Test
    public void openingPurchasePageMustShowPurchaseFormTest() {

        apiClient.receiveCookies(getAuthInfo());

        apiCart.prepareCart()
                .putItemToCart("3158");

        purchasePage.openPurchasePageWithAuthorizedUser(CookieStorage.getCookies())
                .checkPurchaseForm();
    }

    @Test
    public void searchItemMustShowListOfItemsTest() {

        mainPage.openMainPage()
                .search("фитомины");
        searchPage.successfulSearchResultsCheck();
    }
}
