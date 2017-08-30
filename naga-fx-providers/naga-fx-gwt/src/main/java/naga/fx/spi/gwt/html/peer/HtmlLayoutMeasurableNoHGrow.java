package naga.fx.spi.gwt.html.peer;

/**
 * @author Bruno Salmon
 */
public interface HtmlLayoutMeasurableNoHGrow extends HtmlLayoutMeasurable {

    @Override
    default double minWidth(double height) {
        return measureWidth(height);
    }

    @Override
    default double maxWidth(double height) {
        return measureWidth(height);
    }

}
