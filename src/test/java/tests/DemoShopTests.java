package tests;

import org.junit.jupiter.api.Test;
import pages.AccountPage;

public class DemoShopTests extends TestBase {

    @Test
    public void deleteItemFromCartTest() {
        AccountTests account = new AccountTests();
        BookStoreTests bookStore = new BookStoreTests();
        AccountPage accountPage = new AccountPage();


        account.generateToken(getAuthData())
                .login(getAuthData());

        bookStore.cleanLibrary(account.loginResponse)
                .addTestBook(account.loginResponse);

        accountPage.openProfilePageUI(account.loginResponse)
                        .deleteBookUI();

        account.libraryMustBeEmptyCheck();

    }
}
