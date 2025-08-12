package models;

import java.util.Map;

public class AuthDataModel {
    private Map<String, String> authFormData;

    public void setAuthFormData(Map<String, String> authFormData) {
        this.authFormData = authFormData;
    }

    public Map<String, String> getAuthFormData() {
        return this.authFormData;
    }
}
