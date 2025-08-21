package models;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class AuthDataModel {
    private Map<String, String> authData;

    public Map<String, String> getAuthData() {
        return this.authData;
    }

    public void setAuthData(Map<String, String> authFormData) {
        this.authData = authFormData;
    }
}
