package naga.fx.properties.markers;

import javafx.beans.property.ObjectProperty;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

/**
 * @author Bruno Salmon
 */
public interface HasOnMouseClickedProperty {

    default void setOnMouseClicked(EventHandler<? super MouseEvent> value) {
        onMouseClickedProperty().set(value);
    }

    default EventHandler<? super MouseEvent> getOnMouseClicked() {
        return onMouseClickedProperty().get();
    }

    /**
     * Defines a function to be called when a mouse button has been clicked
     * (pressed and released).
     */
    ObjectProperty<EventHandler<? super MouseEvent>> onMouseClickedProperty();

}
