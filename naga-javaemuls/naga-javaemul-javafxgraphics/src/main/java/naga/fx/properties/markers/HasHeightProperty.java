package naga.fx.properties.markers;

import emul.javafx.beans.property.Property;

/**
 * @author Bruno Salmon
 */
public interface HasHeightProperty {

    Property<Double> heightProperty();
    default void setHeight(Double height) { heightProperty().setValue(height); }
    default Double getHeight() { return heightProperty().getValue(); }

}