// Generated by WebFx

module webfx.fxkit.javafx {

    // Direct dependencies modules
    requires java.base;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires jdk.jsobject;
    requires webfx.fxkit.extracontrols;
    requires webfx.fxkit.launcher;
    requires webfx.fxkit.mapper;
    requires webfx.fxkit.mapper.extracontrols;
    requires webfx.fxkit.mapper.javafxgraphics;
    requires webfx.fxkit.util;
    requires webfx.platform.client.uischeduler;
    requires webfx.platform.shared.util;

    // Exported packages
    exports webfx.fxkit.javafx.launcher;
    exports webfx.fxkit.javafx.mapper.peer;
    exports webfx.fxkit.javafx.mapper.peer.extra;
    exports webfx.fxkit.javafx.mapper.peer.skin;
    exports webfx.fxkit.javafx.uischeduler;

    // Provided services
    provides webfx.fxkit.launcher.spi.FxKitLauncherProvider with webfx.fxkit.javafx.launcher.JavaFxFxKitLauncherProvider;
    provides webfx.platform.client.services.uischeduler.spi.UiSchedulerProvider with webfx.fxkit.javafx.uischeduler.FxUiSchedulerProvider;

}