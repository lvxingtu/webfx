package mongoose.operations.backend.route;

import webfx.framework.operation.HasOperationCode;
import webfx.framework.operations.route.RouteRequestBase;
import webfx.platform.client.url.history.History;
import webfx.util.async.AsyncFunction;

/**
 * @author Bruno Salmon
 */
public final class RouteToNewBackendBookingRequest extends RouteRequestBase<RouteToNewBackendBookingRequest>
        implements HasOperationCode {

    private final static String OPERATION_CODE = "RouteToNewBackendBooking";

    private Object eventId;

    public RouteToNewBackendBookingRequest(Object eventId, History history) {
        super(history);
        this.eventId = eventId;
    }

    @Override
    public Object getOperationCode() {
        return OPERATION_CODE;
    }

    public Object getEventId() {
        return eventId;
    }

    public RouteToNewBackendBookingRequest setEventId(Object eventId) {
        this.eventId = eventId;
        return this;
    }

    @Override
    public AsyncFunction<RouteToNewBackendBookingRequest, Void> getOperationExecutor() {
        return RouteToNewBackendBookingExecutor::executeRequest;
    }

}