package webfx.platform.client.url.history.memory;

import webfx.platform.client.url.history.baseimpl.HistoryBase;
import webfx.platform.client.url.history.baseimpl.HistoryLocationImpl;
import webfx.platform.client.url.history.HistoryEvent;
import webfx.platform.client.url.history.HistoryLocation;

import java.util.Stack;

/**
 * @author Bruno Salmon
 */
public class MemoryHistory extends HistoryBase {

    protected final Stack<HistoryLocationImpl> locationStack = new Stack<>();
    protected int backOffset = 0; // offset that becomes > 0 during back navigation to indicate the current location from the top of the history stack

    private int getCurrentLocationIndex() {
        return locationStack.size() - 1 - backOffset;
    }

    @Override
    public HistoryLocationImpl getCurrentLocation() {
        int index = getCurrentLocationIndex();
        return index >= 0 && index < locationStack.size() ? locationStack.get(index) : null;
    }

    @Override
    public void transitionTo(HistoryLocation location) {
        int index = locationStack.indexOf(location);
        if (index > 0)
            go(index - getCurrentLocationIndex());
    }

    @Override
    public void go(int offset) {
        int requestedBackOffset = backOffset - offset;
        if (offset != 0 && requestedBackOffset >= 0 && requestedBackOffset < locationStack.size()) {
            int previousBackOffset = backOffset;
            backOffset = requestedBackOffset;
            HistoryLocationImpl newLocation = getCurrentLocation();
            checkBeforeUnloadThenCheckBeforeThenTransit(newLocation, HistoryEvent.POPPED).setHandler(asyncResult -> {
                if (asyncResult.failed())
                    backOffset = previousBackOffset;
            });
        }
    }

    @Override
    protected void doAcceptedPush(HistoryLocationImpl historyLocation) {
        if (backOffset > 0)
            do
                locationStack.pop();
            while (--backOffset != 0);
        locationStack.push(historyLocation);
    }

    protected void doAcceptedReplace(HistoryLocationImpl historyLocation) {
        int index = getCurrentLocationIndex();
        if (index != -1)
            locationStack.set(index, historyLocation);
        else
            locationStack.push(historyLocation);
    }

}