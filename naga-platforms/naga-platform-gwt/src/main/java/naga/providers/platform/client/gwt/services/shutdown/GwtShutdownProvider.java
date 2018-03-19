package naga.providers.platform.client.gwt.services.shutdown;

import elemental2.dom.DomGlobal;
import naga.platform.services.shutdown.spi.ShutdownProvider;

/**
 * @author Bruno Salmon
 */
public class GwtShutdownProvider implements ShutdownProvider {

    @Override
    public void addShutdownHook(Runnable hook) {
        DomGlobal.window.addEventListener("beforeunload", evt -> hook.run());
    }
}