package mongoose.activities.bothends.logic.ui.calendargraphic;

import mongoose.activities.bothends.logic.calendar.CalendarTimeline;
import javafx.scene.input.MouseEvent;

/**
 * @author Bruno Salmon
 */
public class CalendarClickEvent {

    private final MouseEvent mouseEvent;
    private final CalendarCell calendarCell;
    private final CalendarTimeline calendarTimeline;

    public CalendarClickEvent(MouseEvent mouseEvent, CalendarCell calendarCell, CalendarTimeline calendarTimeline) {
        this.mouseEvent = mouseEvent;
        this.calendarCell = calendarCell;
        this.calendarTimeline = calendarTimeline;
    }

    public MouseEvent getMouseEvent() {
        return mouseEvent;
    }

    public CalendarCell getCalendarCell() {
        return calendarCell;
    }

    public CalendarTimeline getCalendarTimeline() {
        return calendarTimeline;
    }
}