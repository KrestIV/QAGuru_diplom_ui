package helpers;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class CookieStorage {
    private static CookieStorage instance;

    @Getter
    @Setter
    private static Map<String, String> cookies;

    @Getter
    @Setter
    private static String urlPicture;

    public static synchronized CookieStorage getInstance() {
        if (instance == null) {
            instance = new CookieStorage();
        }
        return instance;
    }
}
