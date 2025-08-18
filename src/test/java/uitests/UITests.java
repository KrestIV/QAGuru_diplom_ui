package uitests;

import helpers.AuthDataStorage;
import helpers.CookieStorage;
import helpers.TestDataStorage;
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

        apiCart.checkCartItem(TestDataStorage.getItemCartPrimaryDescription());
    }

    @Test
    public void addingTwoItemsToCartMustDisplayNumberOfItemsInCartTest() {

        apiClient.receiveCookies(AuthDataStorage.getAuthDataContainer());

        apiCart.prepareCart()
                .putItemToCart(TestDataStorage.getItemCartPrimaryId())
                .putItemToCart(TestDataStorage.getItemCartPrimaryId());

        cartPage.openCartPageWithAuthorizedUser(CookieStorage.getCookies())
                .checkFirstItemQuantity();

        apiCart.prepareCart();
    }

    @Test
    public void deletingItemFromCartMustEmptyCartTest() {

        apiClient.receiveCookies(AuthDataStorage.getAuthDataContainer());

        apiCart.prepareCart()
                .putItemToCart(TestDataStorage.getItemCartPrimaryId());

        cartPage.openCartPageWithAuthorizedUser(CookieStorage.getCookies())
                .deleteFirstItem();

        apiCart.checkCartEmpty();
    }

    @Test
    public void clearCartMustEmptyCartTest() {

        apiClient.receiveCookies(AuthDataStorage.getAuthDataContainer());

        apiCart.prepareCart()
                .putItemToCart(TestDataStorage.getItemCartPrimaryId())
                .putItemToCart(TestDataStorage.getItemCartSecondaryId());

        cartPage.openCartPageWithAuthorizedUser(CookieStorage.getCookies())
                .clearCart();

        apiCart.checkCartEmpty();
    }

    @Test
    public void openingPurchasePageMustShowPurchaseFormTest() {

        apiClient.receiveCookies(AuthDataStorage.getAuthDataContainer());

        apiCart.prepareCart()
                .putItemToCart(TestDataStorage.getItemCartPrimaryId());

        purchasePage.openPurchasePageWithAuthorizedUser(CookieStorage.getCookies())
                .checkPurchaseForm();
    }

    @Test
    @Tag("UITests")
    public void searchItemMustShowListOfItemsTest() {

        mainPage.openMainPage()
                .search(TestDataStorage.getSearchWord());
        searchPage.successfulSearchResultsCheck();
    }
}
