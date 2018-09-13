package webfx.fxkits.core.properties.markers;

import javafx.beans.property.DoubleProperty;

/**
 * @author Bruno Salmon
 */
public interface HasMaxProperty {

    DoubleProperty maxProperty();
    default void setMax(Number value) { maxProperty().setValue(value); }
    default Double getMax() { return maxProperty().getValue(); }

}