// Generated by WebFx

module mongoose.client.i18n {

    // Direct dependencies modules
    requires webfx.framework.client.i18n;
    requires webfx.framework.shared.operation;
    requires webfx.platform.shared.util;

    // Exported packages
    exports mongoose.client.operations.i18n;
    exports mongoose.client.services.i18n;

    // Resources packages
    opens mongoose.client.services.i18n.dictionaries;

    // Provided services
    provides webfx.framework.client.operations.i18n.ChangeLanguageRequestEmitter with mongoose.client.operations.i18n.ChangeLanguageToEnglishRequest.ProvidedEmitter, mongoose.client.operations.i18n.ChangeLanguageToFrenchRequest.ProvidedEmitter;
    provides webfx.framework.client.services.i18n.spi.I18nProvider with mongoose.client.services.i18n.MongooseI18nProvider;

}