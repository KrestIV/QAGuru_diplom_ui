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
}
