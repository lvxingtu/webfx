package naga.toolkit.fx.properties.markers;

import javafx.beans.property.Property;

/**
 * @author Bruno Salmon
 */
public interface HasMaxHeightProperty {

    Property<Double> maxHeightProperty();
    default void setMaxHeight(Double maxHeight) { maxHeightProperty().setValue(maxHeight); }
    default Double getMaxHeight() { return maxHeightProperty().getValue(); }

}