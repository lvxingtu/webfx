package naga.providers.platform.client.gwt.url.location;

import naga.platform.client.url.location.WindowLocation;
import naga.util.Strings;

/**
 * @author Bruno Salmon
 */
public final class GwtWindowLocation implements WindowLocation {

    public static GwtWindowLocation current() {
        return new GwtWindowLocation();
    }

    private GwtWindowLocation() {
    }

    @Override
    public native String getHref() /*-{
        return $wnd.location.href;
    }-*/;

    @Override
    public String getProtocol() {
        return Strings.removeSuffix(getWindowProtocol(), ":");
    }

    private native String getWindowProtocol() /*-{
        return $wnd.location.protocol;
    }-*/;

    @Override
    public native String getHost() /*-{
        return $wnd.location.host;
    }-*/;

    @Override
    public native String getHostname() /*-{
        return $wnd.location.hostname;
    }-*/;

    @Override
    public native String getPort() /*-{
        return $wnd.location.port;
    }-*/;

    public native String getPathname() /*-{
        return $wnd.location.pathname;
    }-*/;

    @Override
    public native String getSearch() /*-{
        return $wnd.location.search;
    }-*/;

    @Override
    public String getQueryString() {
        return Strings.removePrefix(getSearch(), "?");
    }

    @Override
    public native String getHash() /*-{
        return $wnd.location.hash;
    }-*/;

    @Override
    public String getFragment() {
        return Strings.removePrefix(getHash(), "#");
    }

    @Override
    public native void assignHref(String href) /*-{
        $wnd.location.assign(href);
    }-*/;

    @Override
    public native void replaceHref(String href) /*-{
        $wnd.location.replace(href);
    }-*/;
}
