// Generated by WebFx

module mongoose.backend.activities.loadtester.jp {

    // Direct dependencies modules
    requires java.base;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires mongoose.backend.activities.loadtester.jp.routing;
    requires mongoose.client.activity;
    requires webfx.framework.client.activity;
    requires webfx.framework.client.controls;
    requires webfx.framework.client.layouts;
    requires webfx.framework.client.uirouter;
    requires webfx.framework.shared.domain;
    requires webfx.framework.shared.entity;
    requires webfx.fxkit.extracontrols;
    requires webfx.platform.client.uischeduler;
    requires webfx.platform.client.websocket;
    requires webfx.platform.client.websocketbus;
    requires webfx.platform.client.windowhistory;
    requires webfx.platform.shared.bus;
    requires webfx.platform.shared.buscall;
    requires webfx.platform.shared.json;
    requires webfx.platform.shared.log;
    requires webfx.platform.shared.scheduler;
    requires webfx.platform.shared.update;
    requires webfx.platform.shared.util;

    // Exported packages
    exports mongoose.backend.activities.loadtester;
    exports mongoose.backend.activities.loadtester.drive;
    exports mongoose.backend.activities.loadtester.drive.command;
    exports mongoose.backend.activities.loadtester.drive.connection;
    exports mongoose.backend.activities.loadtester.drive.listener;
    exports mongoose.backend.activities.loadtester.drive.metrics;
    exports mongoose.backend.activities.loadtester.drive.metrics.controller;
    exports mongoose.backend.activities.loadtester.drive.metrics.model;
    exports mongoose.backend.activities.loadtester.drive.model;
    exports mongoose.backend.activities.saveloadtest;
    exports mongoose.backend.entities.loadtester;
    exports mongoose.backend.entities.loadtester.impl;

    // Provided services
    provides webfx.framework.client.operations.route.RouteRequestEmitter with mongoose.backend.operations.loadtester.RouteToTesterRequest.ProvidedEmitter;
    provides webfx.framework.client.ui.uirouter.UiRoute with mongoose.backend.activities.loadtester.LoadTesterUiRoute, mongoose.backend.activities.saveloadtest.SaveLoadTestUiRoute;
    provides webfx.framework.shared.orm.entity.EntityFactoryProvider with mongoose.backend.entities.loadtester.impl.LtTestEventEntityImpl.ProvidedFactory, mongoose.backend.entities.loadtester.impl.LtTestSetEntityImpl.ProvidedFactory;

}