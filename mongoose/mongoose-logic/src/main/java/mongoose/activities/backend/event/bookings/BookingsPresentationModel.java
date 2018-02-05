package mongoose.activities.backend.event.bookings;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mongoose.activities.shared.generic.eventdependent.EventDependentGenericTablePresentationModel;

import java.time.LocalDate;

/**
 * @author Bruno Salmon
 */
class BookingsPresentationModel extends EventDependentGenericTablePresentationModel {

    private final ObjectProperty<LocalDate> dayProperty = new SimpleObjectProperty<>();
    public final ObjectProperty<LocalDate> dayProperty() { return dayProperty; }
    public final void setDay(LocalDate value) { dayProperty().set(value); }
    public final LocalDate getDay() { return dayProperty().get(); }

    private final ObjectProperty<EventHandler<ActionEvent>> onNewBooking = new SimpleObjectProperty<>();
    public final ObjectProperty<EventHandler<ActionEvent>> onNewBookingProperty() { return onNewBooking; }
    public final void setOnNewBooking(EventHandler<ActionEvent> value) { onNewBookingProperty().set(value); }
    public final EventHandler<ActionEvent> getOnNewBooking() { return onNewBookingProperty().get(); }

    private final ObjectProperty<EventHandler<ActionEvent>> onCloneEvent = new SimpleObjectProperty<>();
    public final ObjectProperty<EventHandler<ActionEvent>> onCloneEventProperty() { return onCloneEvent; }
    public final void setOnCloneEvent(EventHandler<ActionEvent> value) { onCloneEventProperty().set(value); }
    public final EventHandler<ActionEvent> getOnCloneEvent() { return onCloneEventProperty().get(); }

}
