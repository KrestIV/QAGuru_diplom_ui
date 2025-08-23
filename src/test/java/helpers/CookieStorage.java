package helpers;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class CookieStorage {
    @Getter
    @Setter
    private static Map<String, String> cookies;

    @Getter
    @Setter
    private static String urlPicture;
}
