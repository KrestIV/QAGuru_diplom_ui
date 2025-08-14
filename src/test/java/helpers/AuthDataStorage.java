package helpers;

import lombok.Getter;
import lombok.Setter;
import models.AuthDataModel;

public class AuthDataStorage {
    private static AuthDataStorage instance;

    @Getter
    @Setter
    private static AuthDataModel authDataContainer;

    public static synchronized AuthDataStorage getInstance() {
        if (instance == null) {
            instance = new AuthDataStorage();
        }
        return instance;
    }
}
