package ui;

import org.junit.jupiter.api.Test;
import pages.*;
import steps.CartAPISteps;
import steps.LoginAPISteps;

public class UITests extends UIBaseTest {

    //UI Tests
    @Test
    public void loginWithCorrectCredentialsMustGreetUserTest() {

        MainPage mainPage = new MainPage();

        mainPage.openMainPage()
                .login(getAuthInfo())
                .checkLogin();

    }

    @Test
    public void addingItemToCartMustAddItemToCartTest(){

        LoginAPISteps client = new LoginAPISteps();
        DogFoodPage dogFoodPage = new DogFoodPage();
        CartAPISteps cart = new CartAPISteps();

        client.login(getAuthInfo());

        cart.clearCart(client.getCookies());

        dogFoodPage.openPageWithAuthorizedUser(client.getCookies())
                .addItemToCart();

        cart.getCart(client.getCookies())
                .checkCart("Brit Fresh Chicken")
                .clearCart(client.getCookies());

    }

    @Test
    public void addingTwoItemsToCartMustDisplayNumberOfItemsInCartTest(){

        LoginAPISteps client = new LoginAPISteps();
        CartPage cartPage = new CartPage();
        CartAPISteps cart = new CartAPISteps();

        client.login(getAuthInfo());

        cart.clearCart(client.getCookies())
                .putItemToCart(client.getCookies(),"3158")
                .putItemToCart(client.getCookies(),"3158");

        cartPage.openCartPageWithAuthorizedUser(client.getCookies())
                .checkFirstItemQuantity();

        cart.clearCart(client.getCookies());
    }

    @Test
    public void deletingItemFromCartMustEmptyCartTest(){
        LoginAPISteps client = new LoginAPISteps();
        CartPage cartPage = new CartPage();
        CartAPISteps cart = new CartAPISteps();

        client.login(getAuthInfo());

        cart.clearCart(client.getCookies())
                .putItemToCart(client.getCookies(),"3158");

        cartPage.openCartPageWithAuthorizedUser(client.getCookies())
                .deleteFirstItem();

        cart.getCart(client.getCookies())
                .checkCart("Корзина пуста");
    }

    @Test
    public void clearCartMustEmptyCartTest(){
        LoginAPISteps client = new LoginAPISteps();
        CartPage cartPage = new CartPage();
        CartAPISteps cart = new CartAPISteps();

        client.login(getAuthInfo());

        cart.clearCart(client.getCookies())
                .putItemToCart(client.getCookies(),"3158")
                .putItemToCart(client.getCookies(),"721468");

        cartPage.openCartPageWithAuthorizedUser(client.getCookies())
                .clearCart();

        cart.getCart(client.getCookies())
                .checkCart("Корзина пуста");

    }

    @Test
    public void openingPurchasePageMustShowPurchaseFormTest(){
        LoginAPISteps client = new LoginAPISteps();
        PurchasePage purchasePage = new PurchasePage();
        CartAPISteps cart = new CartAPISteps();

        client.login(getAuthInfo());

        cart.clearCart(client.getCookies())
                .putItemToCart(client.getCookies(),"3158");

        purchasePage.openPurchasePageWithAuthorizedUser(client.getCookies())
                .checkPurchaseForm();

        cart.clearCart(client.getCookies());
    }

    @Test
    public void searchItemMustShowListOfItemsTest(){
        MainPage mainPage = new MainPage();
        SearchPage searchPage = new SearchPage();

        mainPage.openMainPage()
                .search("фитомины");
        searchPage.successfulSearchResultsCheck();
    }
}
