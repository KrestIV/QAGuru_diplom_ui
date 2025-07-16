package tests;

import org.junit.jupiter.api.Test;

public class DemoShopTests extends TestBase {

    @Test
    public void deleteItemFromCartTestNew() {
        StepsTest stepTest = new StepsTest();

        stepTest.generateToken(getAuthData())
                .login(getAuthData())
                .cleanLibrary()
                .addTestBook()
                .openProfilePageUI()
                .deleteBookUI()
                .libraryMustBeEmptyCheck();
    }

    @Test
    public void deleteItemFromCartTest() {
        AccountTests account = new AccountTests();
        BookStoreTests bookStore = new BookStoreTests();

        account.generateToken(getAuthData())
                .login(getAuthData());
        bookStore.cleanLibrary(account.loginResponse)
                .addTestBook(account.loginResponse);

        account.libraryMustBeEmptyCheck();

    }
}
