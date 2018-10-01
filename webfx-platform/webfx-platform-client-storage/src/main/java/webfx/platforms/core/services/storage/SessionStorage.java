package webfx.platforms.core.services.storage;

import webfx.platforms.core.services.storage.spi.SessionStorageProvider;
import webfx.platforms.core.util.serviceloader.SingleServiceLoader;

import java.util.Iterator;

/**
 * @author Bruno Salmon
 */
public final class SessionStorage {

    public static SessionStorageProvider getProvider() {
        return SingleServiceLoader.loadService(SessionStorageProvider.class);
    }

    public static void setItem(String key, String value) {
        getProvider().setItem(key, value);
    }

    public static String getItem(String key) {
        return getProvider().getItem(key);
    }

    public static void removeItem(String key) {
        getProvider().removeItem(key);
    }

    public static Iterator<String> getKeys() {
        return getProvider().getKeys();
    }

    public static int length() {
        return getProvider().getLength();
    }

    public static void clear() {
        getProvider().clear();
    }

}