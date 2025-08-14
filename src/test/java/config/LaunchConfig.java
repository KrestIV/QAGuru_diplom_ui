package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:${platform}.config",
        "classpath:${platform}.items.config",
        "system:properties"
})
public interface LaunchConfig extends Config {
    @Key("url.base")
    String getBaseUrl();

    @Key("browser.name")
    String getBrowserName();

    @Key("browser.size")
    String getBrowserSize();

    @Key("server")
    String getServerAddress();

    @Key("login")
    String getShopLogin();

    @Key("pw")
    String getShopPassword();

    @Key("item.forcart.primary.id")
    String getItemForcartPrimaryId();

}
