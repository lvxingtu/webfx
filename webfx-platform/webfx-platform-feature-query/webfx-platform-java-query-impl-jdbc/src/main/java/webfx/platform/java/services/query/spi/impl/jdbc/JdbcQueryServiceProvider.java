package webfx.platform.java.services.query.spi.impl.jdbc;

import webfx.platform.java.services_shared_code.queryupdate.jdbc.JdbcLocalConnectedQueryUpdateServiceProvider;
import webfx.platform.shared.services.datasource.LocalDataSource;
import webfx.platform.shared.services.query.spi.QueryServiceProvider;
import webfx.platform.shared.services.query.spi.impl.LocalOrRemoteQueryServiceProvider;

/**
 * @author Bruno Salmon
 */
public final class JdbcQueryServiceProvider extends LocalOrRemoteQueryServiceProvider {

    @Override
    protected QueryServiceProvider createLocalConnectedProvider(LocalDataSource localDataSource) {
        return new JdbcLocalConnectedQueryUpdateServiceProvider(localDataSource);
    }

}
