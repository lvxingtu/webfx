package webfx.platforms.core.services.websocket;

import webfx.platforms.core.services.json.JsonObject;
import webfx.platforms.core.services.websocket.spi.WebSocketServiceProvider;
import webfx.platforms.core.util.serviceloader.SingleServiceLoader;

/**
 * @author Bruno Salmon
 */
public final class WebSocketService {

    public static WebSocketServiceProvider getProvider() {
        return SingleServiceLoader.loadService(WebSocketServiceProvider.class);
    }

    public static WebSocket createWebSocket(String url, JsonObject options) {
        return getProvider().createWebSocket(url, options);
    }

}