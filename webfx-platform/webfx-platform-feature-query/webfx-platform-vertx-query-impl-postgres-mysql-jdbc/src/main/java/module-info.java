// Generated by WebFx

module webfx.platform.vertx.query.impl.postgres.mysql.jdbc {

    // Direct dependencies modules
    requires webfx.platform.shared.datasource;
    requires webfx.platform.shared.query;
    requires webfx.platform.vertx.queryupdate;

    // Exported packages
    exports webfx.platform.vertx.services.query.spi.impl;

    // Provided services
    provides webfx.platform.shared.services.query.spi.QueryServiceProvider with webfx.platform.vertx.services.query.spi.impl.VertxQueryServiceProvider;

}