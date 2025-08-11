package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:${platform}.config"
})
public interface LaunchConfig extends Config {
    @Key("browser.name")
    @DefaultValue("chrome")
    String getBrowserName();

    @Key("browser.version")
    String getBrowserVersion();

    @Key("browser.size")
    @DefaultValue("1920x1080")
    String getBrowserSize();

    @Key("server.address")
    String getServerAddress();

    @Key("login")
    String getLogin();

    @Key("pw")
    String getPassword();

    @Key("shop.login")
    String getShopLogin();

    @Key("shop.pw")
    String getShopPassword();

}
