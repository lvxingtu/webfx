package naga.providers.toolkit.swing.fx.view;

import naga.toolkit.fx.scene.layout.Region;
import naga.toolkit.fx.spi.view.base.RegionViewBase;
import naga.toolkit.fx.spi.view.base.RegionViewMixin;

/**
 * @author Bruno Salmon
 */
public abstract class SwingRegionView
        <N extends Region, NV extends RegionViewBase<N, NV, NM>, NM extends RegionViewMixin<N, NV, NM>>

        extends SwingNodeView<N, NV, NM>
        implements RegionViewMixin<N, NV, NM> {


    SwingRegionView(NV base) {
        super(base);
    }

    @Override
    public void updateWidth(Double width) {
        updateSize();
    }

    @Override
    public void updateHeight(Double height) {
        updateSize();
    }

    protected void updateSize() {
    }
}
