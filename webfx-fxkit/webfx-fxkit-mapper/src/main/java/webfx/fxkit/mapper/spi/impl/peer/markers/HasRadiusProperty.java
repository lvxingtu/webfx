package webfx.fxkit.mapper.spi.impl.peer.markers;

import javafx.beans.property.Property;

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