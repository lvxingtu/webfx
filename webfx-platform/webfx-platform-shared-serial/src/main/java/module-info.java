// Generated by WebFx

module webfx.platform.shared.serial {

    // Direct dependencies modules
    requires java.base;
    requires webfx.platform.shared.appcontainer;
    requires webfx.platform.shared.json;
    requires webfx.platform.shared.log;
    requires webfx.platform.shared.util;

    // Exported packages
    exports webfx.platform.shared.services.serial;
    exports webfx.platform.shared.services.serial.spi;
    exports webfx.platform.shared.services.serial.spi.impl;

    // Used services
    uses webfx.platform.shared.services.serial.spi.SerialCodec;

    // Provided services
    provides webfx.platform.shared.services.appcontainer.spi.ApplicationModuleInitializer with webfx.platform.shared.services.serial.SerialCodecModuleInitializer;
    provides webfx.platform.shared.services.serial.spi.SerialCodec with webfx.platform.shared.services.serial.spi.impl.ProvidedBatchSerialCodec;

}