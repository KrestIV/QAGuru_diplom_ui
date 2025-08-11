package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:${platform}.config",
        "system:properties"
})
public interface LaunchConfig extends Config {
    @Key("browser.name")
    String getBrowserName();

    @Key("browser.version")
    String getBrowserVersion();

    @Key("browser.size")
    String getBrowserSize();

    @Key("server.address")
    String getServerAddress();

    @Key("login")
    String getShopLogin();

    @Key("pw")
    String getShopPassword();

}
