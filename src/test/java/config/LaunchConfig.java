package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "classpath:${platform}.config",
        "classpath:${testItems}.config",
        "system:properties"
})
public interface LaunchConfig extends Config {
    @Key("url.base")
    String getBaseUrl();

    @Key("browser.name")
    String getBrowserName();

    @Key("browser.version")
    String getBrowserVersion();

    @Key("browser.size")
    String getBrowserSize();

    @Key("server")
    String getServerAddress();

    @Key("login")
    String getShopLogin();

    @Key("pw")
    String getShopPassword();

    @Key("item.cart.primary.id")
    String getItemCartPrimaryId();

    @Key("item.cart.primary.description")
    String getItemCartPrimaryDescription();

    @Key("item.cart.secondary.id")
    String getItemCartSecondaryId();

    @Key("search.word")
    String getSearchWord();

}
