package webfx.framework.activity.base.elementals.view.impl;

import javafx.scene.Node;
import webfx.util.async.Future;
import webfx.framework.activity.base.elementals.uiroute.impl.UiRouteActivityBase;
import webfx.framework.activity.base.elementals.view.ViewActivity;
import webfx.framework.activity.base.elementals.view.ViewActivityContext;
import webfx.framework.activity.base.elementals.view.ViewActivityContextMixin;
import webfx.fx.spi.Toolkit;

/**
 * @author Bruno Salmon
 */
public abstract class ViewActivityBase
        <C extends ViewActivityContext<C>>

        extends UiRouteActivityBase<C>
        implements ViewActivity<C>,
        ViewActivityContextMixin<C> {

    private Node uiNode;

    @Override
    public Future<Void> onResumeAsync() {
        if (Toolkit.get().isReady())
            return Future.runAsync(this::onResume);
        Future<Void> future = Future.future();
        Toolkit.get().onReady(() -> {
            onResume();
            future.complete();
        });
        return future;
    }

    @Override
    public void onResume() {
        super.onResume(); // will update context parameters from route and make the active property to true
        if (uiNode == null) {
            startLogic(); // The good place to start the logic (before building ui but after the above update)
            uiNode = buildUi();
        }
        setNode(uiNode);
    }

    protected void startLogic() {
    }
}