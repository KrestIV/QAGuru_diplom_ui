package uitests;

import helpers.AuthDataStorage;
import helpers.CookieStorage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.*;
import uiapisteps.CartAPISteps;
import uiapisteps.LoginAPISteps;

@Tag("FullTest")
public class UITests extends UIBaseTest {

    MainPage mainPage = new MainPage();
    SearchPage searchPage = new SearchPage();
    PurchasePage purchasePage = new PurchasePage();
    CartPage cartPage = new CartPage();
    DogFoodPage dogFoodPage = new DogFoodPage();
    LoginAPISteps apiClient = new LoginAPISteps();
    CartAPISteps apiCart = new CartAPISteps();

    @Test
    public void loginWithCorrectCredentialsMustGreetUserTest() {

        mainPage.openMainPage()
                .login(AuthDataStorage.getAuthDataContainer())
                .checkLogin();
    }

    @Test
    public void addingItemToCartMustAddItemToCartTest() {

        apiClient.receiveCookies(AuthDataStorage.getAuthDataContainer());

        apiCart.prepareCart();

        dogFoodPage.openPageWithAuthorizedUser(CookieStorage.getCookies())
                .addItemToCart();

        apiCart.checkCart("Brit Fresh Chicken");
    }

    @Test
    public void addingTwoItemsToCartMustDisplayNumberOfItemsInCartTest() {

        apiClient.receiveCookies(AuthDataStorage.getAuthDataContainer());

        apiCart.prepareCart()
                .putItemToCart("3285")
                .putItemToCart("3285");

        cartPage.openCartPageWithAuthorizedUser(CookieStorage.getCookies())
                .checkFirstItemQuantity();

        apiCart.prepareCart();
    }

    @Test
    public void deletingItemFromCartMustEmptyCartTest() {

        apiClient.receiveCookies(AuthDataStorage.getAuthDataContainer());

        apiCart.prepareCart()
                .putItemToCart("3285");

        cartPage.openCartPageWithAuthorizedUser(CookieStorage.getCookies())
                .deleteFirstItem();

        apiCart.checkCart("Корзина пуста");
    }

    @Test
    public void clearCartMustEmptyCartTest() {

        apiClient.receiveCookies(AuthDataStorage.getAuthDataContainer());

        apiCart.prepareCart()
                .putItemToCart("3285")
                .putItemToCart("721468");

        cartPage.openCartPageWithAuthorizedUser(CookieStorage.getCookies())
                .clearCart();

        apiCart.checkCart("Корзина пуста");
    }

    @Test
    public void openingPurchasePageMustShowPurchaseFormTest() {

        apiClient.receiveCookies(AuthDataStorage.getAuthDataContainer());

        apiCart.prepareCart()
                .putItemToCart("3285");

        purchasePage.openPurchasePageWithAuthorizedUser(CookieStorage.getCookies())
                .checkPurchaseForm();
    }

    @Test
    @Tag("UITests")
    public void searchItemMustShowListOfItemsTest() {

        mainPage.openMainPage()
                .search("фитомины");
        searchPage.successfulSearchResultsCheck();
    }
}
