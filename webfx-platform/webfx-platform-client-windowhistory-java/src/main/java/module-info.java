// Generated by WebFx

module webfx.platform.client.windowhistory.java {

    // Direct dependencies modules
    requires webfx.platform.client.windowhistory;

    // Exported packages
    exports webfx.platform.client.services.windowhistory.spi.impl.java;

    // Provided services
    provides webfx.platform.client.services.windowhistory.spi.WindowHistoryProvider with webfx.platform.client.services.windowhistory.spi.impl.java.JavaWindowHistoryProvider;

}