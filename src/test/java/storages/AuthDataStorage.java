package storages;

import lombok.Getter;
import lombok.Setter;
import models.AuthDataModel;


public class AuthDataStorage {
    @Getter
    @Setter
    private static AuthDataModel authDataContainer;
}
