package naga.platform.services.auth.spi;

import naga.commons.util.async.Future;

/**
 * @author Bruno Salmon
 */
public interface User {

    Future<Boolean> isAuthorized(Object authority);

}