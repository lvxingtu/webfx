package webfx.framework.operations.route;

import webfx.framework.operation.HasOperationCode;
import webfx.platform.client.url.history.History;
import webfx.util.async.AsyncFunction;

/**
 * @author Bruno Salmon
 */
public final class RouteBackwardRequest
        extends RouteRequestBase<RouteBackwardRequest>
        implements HasOperationCode {

    private static final String OPERATION_CODE = "RouteBackward";

    public RouteBackwardRequest(History history) {
        super(history);
    }

    @Override
    public Object getOperationCode() {
        return OPERATION_CODE;
    }

    @Override
    public AsyncFunction<RouteBackwardRequest, Void> getOperationExecutor() {
        return RouteBackwardExecutor::executeRequest;
    }
}