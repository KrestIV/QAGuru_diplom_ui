package api;

import org.junit.jupiter.api.Test;
import steps.CartAPISteps;
import steps.LoginAPISteps;
import steps.ProfileAPISteps;

public class APITests extends APIBaseTests {

    @Test
    public void authorizeWithWrongPasswordShouldReturnErrorTest() {
        LoginAPISteps client = new LoginAPISteps();

        client.loginWithWrongPassword(getAuthInfo("break"))
                .checkLoginError();
    }

    @Test
    public void addedItemMustBeInCartTest() {
        LoginAPISteps client = new LoginAPISteps();
        CartAPISteps cart = new CartAPISteps();

        client.login(getAuthInfo(""));

        cart.clearCart(client.getCookies())
                .putItemToCart(client.getCookies(), "3285")
                .getCart(client.getCookies())
                .checkCart("Brit Fresh Chicken")
                .clearCart(client.getCookies());

    }

    @Test
    public void afterCleaningCartMustBeEmptyTest() {

        LoginAPISteps client = new LoginAPISteps();
        CartAPISteps cart = new CartAPISteps();

        client.login(getAuthInfo(""));

        cart.putItemToCart(client.getCookies(), "3158")
                .clearCart(client.getCookies())
                .getCart(client.getCookies())
                .checkCart("Корзина пуста");

    }

    @Test
    public void addingNonexistentItemToCartMustReturnErrorTest() {

        LoginAPISteps client = new LoginAPISteps();
        CartAPISteps cart = new CartAPISteps();

        client.login(getAuthInfo(""));

        cart.clearCart(client.getCookies())
                .putItemToCart(client.getCookies(), "999999999")
                .getCart(client.getCookies())
                .checkCart("Корзина пуста");

    }

    @Test
    public void changingProfileMustBeSavedInProfileTest() {
        LoginAPISteps client = new LoginAPISteps();
        ProfileAPISteps profile = new ProfileAPISteps();

        client.login(getAuthInfo(""));
        profile.changeSettings(profile.getNewSettingsFormData(), client.getCookies())
                .getSettings(client.getCookies())
                .checkSettings()
                .changeSettings(profile.getOldSettingsFormData(), client.getCookies());

    }
}
