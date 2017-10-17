package naga.providers.platform.client.teavm.url.location;


import naga.platform.client.url.location.WindowLocation;
import naga.util.Strings;
import org.teavm.jso.browser.Location;

/**
 * @author Bruno Salmon
 */
public class TeaVmWindowLocation implements WindowLocation {

    private final Location jsoLocation;

    public static TeaVmWindowLocation current() {
        return new TeaVmWindowLocation(org.teavm.jso.browser.Location.current());
    }

    TeaVmWindowLocation(org.teavm.jso.browser.Location jsoLocation) {
        this.jsoLocation = jsoLocation;
    }

    @Override
    public String getHref() {
        return jsoLocation.getFullURL();
    }

    @Override
    public String getProtocol() {
        return jsoLocation.getProtocol();
    }

    @Override
    public String getHost() {
        return jsoLocation.getHost();
    }

    @Override
    public String getHostname() {
        return jsoLocation.getHostName();
    }

    @Override
    public String getPort() {
        return jsoLocation.getPort();
    }

    public String getPathname() {
        return jsoLocation.getPathName();
    }

    @Override
    public String getSearch() {
        return jsoLocation.getSearch();
    }

    @Override
    public String getQueryString() {
        return Strings.removePrefix(getSearch(), "?");
    }

    @Override
    public String getHash() {
        return jsoLocation.getHash();
    }

    @Override
    public String getFragment() {
        return Strings.removePrefix(getHash(), "#");
    }
}
