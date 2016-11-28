package naga.providers.toolkit.swing.drawing.view;

import naga.toolkit.drawing.layout.Region;
import naga.toolkit.drawing.spi.view.base.RegionViewBase;
import naga.toolkit.drawing.spi.view.base.RegionViewMixin;

/**
 * @author Bruno Salmon
 */
public class SwingRegionView<R extends Region>
        extends SwingNodeView<R, RegionViewBase<R>, RegionViewMixin<R>>
        implements RegionViewMixin<R> {

    public SwingRegionView() {
        super(new RegionViewBase<>());
    }
}
