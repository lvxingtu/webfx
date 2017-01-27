package mongoose.activities.shared.application.web;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import naga.providers.platform.client.gwt.services.resource.GwtBundle;

/**
 * @author Bruno Salmon
 */
public interface WebMongooseBundle extends ClientBundle {

    WebMongooseBundle R = GWT.create(WebMongooseBundle.class);

    @Source("mongoose/domainmodel/DomainModelSnapshot.lzb64json")
    TextResource lzBase64DomainModelSnapshot();

    @Source("naga/platform/client/bus/BusOptions.json")
    TextResource jsonClientBusOptions();

    @Source("mongoose/dictionaries/en.json")
    TextResource englishDictionary();

    @Source("mongoose/dictionaries/fr.json")
    TextResource frenchDictionary();

    @Source("images/certificate.svg")
    TextResource certificateSvg();

    @Source("images/calendar.svg")
    TextResource calendarSvg();

    GwtBundle B = resourcePath -> {
        switch (resourcePath) {
            case "mongoose/domainmodel/DomainModelSnapshot.lzb64json": return R.lzBase64DomainModelSnapshot();
            case "naga/platform/client/bus/BusOptions.json": return R.jsonClientBusOptions();
            case "mongoose/dictionaries/en.json": return R.englishDictionary();
            case "mongoose/dictionaries/fr.json": return R.frenchDictionary();
            case "images/certificate.svg": return R.certificateSvg();
            case "images/calendar.svg": return R.calendarSvg();
            default: return null;
        }
    };
}