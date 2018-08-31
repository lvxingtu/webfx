package mongoose.activities.bothends.book.options;

import mongoose.activities.bothends.generic.routing.MongooseRoutingUtil;
import webfx.framework.activity.base.combinations.viewdomain.impl.ViewDomainActivityContextFinal;
import webfx.framework.ui.uirouter.UiRoute;

/**
 * @author Bruno Salmon
 */
public final class OptionsRouting {

    private static final String PATH = "/book/event/:eventId/options";

    public static UiRoute<?> uiRoute() {
        return UiRoute.create(PATH
                , false
                , OptionsActivity::new
                , ViewDomainActivityContextFinal::new
        );
    }

    public static String getPath() {
        return PATH;
    }

    public static String getEventOptionsPath(Object eventId) {
        return MongooseRoutingUtil.interpolateEventIdInPath(eventId, getPath());
    }
}