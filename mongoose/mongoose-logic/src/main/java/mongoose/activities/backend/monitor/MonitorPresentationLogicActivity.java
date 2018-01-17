package mongoose.activities.backend.monitor;

import naga.framework.activity.combinations.domainpresentationlogic.impl.DomainPresentationLogicActivityImpl;
import naga.framework.ui.filter.ReactiveExpressionFilterFactoryMixin;

/**
 * @author Bruno Salmon
 */
public class MonitorPresentationLogicActivity
        extends DomainPresentationLogicActivityImpl<MonitorPresentationModel>
        implements ReactiveExpressionFilterFactoryMixin {

    public MonitorPresentationLogicActivity() {
        super(MonitorPresentationModel::new);
    }

    @Override
    protected void startLogic(MonitorPresentationModel pm) {
        createReactiveExpressionFilter("{class: 'Metrics', orderBy: 'date desc', limit: '500'}")
                .setExpressionColumns("['0 + id','memoryUsed','memoryTotal']")
                .displayResultSetInto(pm.memoryDisplayResultSetProperty())
                .nextDisplay()
                .setExpressionColumns("['0 + id','systemLoadAverage','processCpuLoad']")
                //.combine("{columns: '0 + id,systemLoadAverage,processCpuLoad'}")
                .displayResultSetInto(pm.cpuDisplayResultSetProperty())
                .setAutoRefresh(true)
                .start();
    }
}
