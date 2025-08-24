package uitests;

import storages.AuthDataStorage;
import storages.TestDataStorage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.*;
import uiapisteps.CommonAPISteps;

@Tag("FullTest")
public class UITests extends UIBaseTest {

    MainPage mainPage = new MainPage();
    SearchPage searchPage = new SearchPage();
    PurchasePage purchasePage = new PurchasePage();
    CartPage cartPage = new CartPage();
    DogFoodPage dogFoodPage = new DogFoodPage();
    CommonAPISteps commonAPISteps = new CommonAPISteps();

    @Test
    public void loginWithCorrectCredentialsMustGreetUserTest() {

        mainPage
                .openMainPage()
                .login(AuthDataStorage.getAuthDataContainer())
                .checkLogin();
    }

    @Test
    @Tag("CartTests")
    public void addingItemToCartMustAddItemToCartTest() {

        commonAPISteps
                .receiveCookies(AuthDataStorage.getAuthDataContainer())
                .prepareCart();

        dogFoodPage
                .openPageWithAuthorizedUser()
                .addItemToCart();

        commonAPISteps
                .checkCartItem(TestDataStorage.getItemCartPrimaryDescription());
    }

    @Test
    @Tag("CartTests")
    public void addingTwoItemsToCartMustDisplayNumberOfItemsInCartTest() {
        int quantity = (int) (Math.random() * 3) + 2;

        commonAPISteps
                .receiveCookies(AuthDataStorage.getAuthDataContainer())
                .prepareCart()
                .putItemToCart(TestDataStorage.getItemCartPrimaryId(), quantity);

        cartPage
                .openCartPageWithAuthorizedUser()
                .checkFirstItemQuantity(quantity);

        commonAPISteps
                .prepareCart();
    }

    @Test
    @Tag("CartTests")
    public void deletingItemFromCartMustEmptyCartTest() {

        commonAPISteps
                .receiveCookies(AuthDataStorage.getAuthDataContainer())
                .prepareCart()
                .putItemToCart(TestDataStorage.getItemCartPrimaryId());

        cartPage
                .openCartPageWithAuthorizedUser()
                .deleteFirstItem();

        commonAPISteps
                .checkCartEmpty();
    }

    @Test
    @Tag("CartTests")
    public void clearCartMustEmptyCartTest() {

        commonAPISteps
                .receiveCookies(AuthDataStorage.getAuthDataContainer())
                .prepareCart()
                .putItemToCart(TestDataStorage.getItemCartPrimaryId())
                .putItemToCart(TestDataStorage.getItemCartSecondaryId());

        cartPage
                .openCartPageWithAuthorizedUser()
                .clearCart();

        commonAPISteps
                .checkCartEmpty();
    }

    @Test
    public void openingPurchasePageMustShowPurchaseFormTest() {

        commonAPISteps
                .receiveCookies(AuthDataStorage.getAuthDataContainer())
                .prepareCart()
                .putItemToCart(TestDataStorage.getItemCartPrimaryId());

        purchasePage
                .openPurchasePageWithAuthorizedUser()
                .checkPurchaseForm();
    }

    @Test
    public void searchItemMustShowListOfItemsTest() {

        mainPage
                .openMainPage()
                .search(TestDataStorage.getSearchWord());

        searchPage
                .successfulSearchResultsCheck();
    }
}
