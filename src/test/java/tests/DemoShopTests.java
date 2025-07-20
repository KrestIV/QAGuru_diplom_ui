package tests;

import org.junit.jupiter.api.Test;
import pages.AccountPage;
import steps.AccountApiSteps;
import steps.BookStoreApiSteps;

public class DemoShopTests extends TestBase {

    @Test
    public void deleteItemFromCartTest() {
        AccountApiSteps account = new AccountApiSteps();
        BookStoreApiSteps bookStore = new BookStoreApiSteps();
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
