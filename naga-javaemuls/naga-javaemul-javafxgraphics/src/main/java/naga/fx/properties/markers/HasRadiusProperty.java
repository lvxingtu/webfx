package naga.fx.properties.markers;

import emul.javafx.beans.property.Property;

/**
 * @author Bruno Salmon
 */
public interface HasRadiusProperty {

    Property<Double> radiusProperty();

    default void setRadius(Double height) {
        radiusProperty().setValue(height);
    }

    default Double getRadius() {
        return radiusProperty().getValue();
    }

}