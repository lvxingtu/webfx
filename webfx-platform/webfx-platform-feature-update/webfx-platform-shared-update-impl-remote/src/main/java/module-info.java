// Generated by WebFx

module webfx.platform.shared.update.impl.remote {

    // Direct dependencies modules
    requires webfx.platform.shared.update;

    // Exported packages
    exports webfx.platform.shared.services.update.spi.impl.remote;

    // Provided services
    provides webfx.platform.shared.services.update.spi.UpdateServiceProvider with webfx.platform.shared.services.update.spi.impl.remote.RemoteUpdateServiceProvider;

}