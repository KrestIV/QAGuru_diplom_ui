package models;

import java.util.Map;

public class AuthDataModel {
    private Map<String, String> authData;

    public void setAuthData(Map<String, String> authFormData) {
        this.authData = authFormData;
    }

    public Map<String, String> getAuthData() {
        return this.authData;
    }
}
