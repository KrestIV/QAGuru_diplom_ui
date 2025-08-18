package helpers;

import lombok.Getter;
import lombok.Setter;

public class TestDataStorage {
    private static TestDataStorage instance;

    @Getter
    @Setter
    private static String
            itemCartPrimaryId,
            itemCartPrimaryDescription,
            itemCartSecondaryId,
            searchWord;

    public static synchronized TestDataStorage getInstance() {
        if (instance == null) {
            instance = new TestDataStorage();
        }
        return instance;
    }
}
