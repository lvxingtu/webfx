package webfx.framework.activity.base.elementals.uiroute;

import webfx.framework.activity.base.elementals.activeproperty.ActivePropertyActivityContext;
import webfx.framework.router.session.Session;
import webfx.framework.ui.uirouter.UiRouter;
import webfx.framework.ui.uisession.UiSession;
import webfx.platform.client.url.history.History;
import webfx.platform.services.json.JsonObject;

/**
 * @author Bruno Salmon
 */
public interface UiRouteActivityContext
        <THIS extends UiRouteActivityContext<THIS>>

        extends ActivePropertyActivityContext<THIS> {

    UiRouter getUiRouter();

    default History getHistory() {
        return getUiRouter().getHistory();
    }

    JsonObject getParams();

    default <T> T getParameter(String key) { return getParams().get(key); }

    Session getSession();

    String getRoutingPath();

    default UiSession getUiSession() {
        return getUiRouter().getUiSession();
    }
}