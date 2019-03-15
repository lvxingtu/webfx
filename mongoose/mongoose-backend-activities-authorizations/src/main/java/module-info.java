// Generated by WebFx

module mongoose.backend.activities.authorizations {

    // Direct dependencies modules
    requires javafx.base;
    requires javafx.graphics;
    requires mongoose.backend.activities.authorizations.routing;
    requires mongoose.client.authn;
    requires webfx.framework.client.activity;
    requires webfx.framework.client.controls;
    requires webfx.framework.client.layouts;
    requires webfx.framework.client.uifilter;
    requires webfx.framework.client.uirouter;
    requires webfx.framework.shared.domain;
    requires webfx.framework.shared.entity;
    requires webfx.framework.shared.router;
    requires webfx.fxkit.extracontrols;
    requires webfx.platform.shared.util;

    // Exported packages
    exports mongoose.backend.activities.authorizations;

    // Provided services
    provides webfx.framework.client.operations.route.RouteRequestEmitter with mongoose.backend.activities.authorizations.RouteToAuthorizationsRequestEmitter;
    provides webfx.framework.client.ui.uirouter.UiRoute with mongoose.backend.activities.authorizations.AuthorizationsUiRoute;

}