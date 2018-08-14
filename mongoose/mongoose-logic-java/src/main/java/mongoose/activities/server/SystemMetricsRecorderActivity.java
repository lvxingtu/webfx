package mongoose.activities.server;

import mongoose.domainmodel.loader.DomainModelSnapshotLoader;
import mongoose.entities.SystemMetricsEntity;
import mongoose.services.systemmetrics.SystemMetricsService;
import naga.framework.activity.domain.DomainActivityContext;
import naga.framework.activity.domain.DomainActivityContextMixin;
import naga.framework.orm.entity.UpdateStore;
import naga.platform.activity.Activity;
import naga.platform.activity.ActivityManager;
import naga.platform.services.log.Logger;
import naga.platform.services.scheduler.Scheduled;
import naga.platform.services.scheduler.Scheduler;
import naga.platform.services.update.UpdateArgument;
import naga.platform.services.update.UpdateService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * @author Bruno Salmon
 */
public class SystemMetricsRecorderActivity implements Activity<DomainActivityContext>, DomainActivityContextMixin {

    private DomainActivityContext activityContext;
    private Scheduled metricsCapturePeriodicTimer;
    private Scheduled metricsCleaningPeriodicTimer;

    @Override
    public void onCreate(DomainActivityContext context) {
        this.activityContext = context;
    }

    public DomainActivityContext getActivityContext() {
        return activityContext;
    }

    @Override
    public void onStart() {
        // Stopping the activity if there is actually no metrics service registered for this platform
        if (SystemMetricsService.getProvider() == null) {
            Logger.log("SystemMetricsRecorderActivity will not start as no SystemMetricsServiceProvider is registered for this platform");
            getActivityManager().destroy(); // Asking the activity manager to stop and destroy this activity
            return;
        }

        Logger.log("Starting system metrics recorder activity...");
        // Starting a periodic timer to capture metrics every seconds and record it in the database
        metricsCapturePeriodicTimer = Scheduler.schedulePeriodic(1000, () -> {
            // Creating an update store for metrics entity
            UpdateStore store = UpdateStore.create(getDataSourceModel());
            // Instantiating a new system metrics entity and asking the system metrics service to fill that entity
            SystemMetricsService.takeSystemMetricsSnapshot(store.insertEntity(SystemMetricsEntity.class));
            // Asking the update store to record this in the database
            store.executeUpdate().setHandler(asyncResult -> {
                if (asyncResult.failed())
                    Logger.log("Inserting metrics in database failed!", asyncResult.cause());
            });
        });

        // Deleting old metrics records (older than 1 day) regularly (every 12h)
        metricsCleaningPeriodicTimer = Scheduler.schedulePeriodic(12 * 3600 * 1000, () ->
            UpdateService.executeUpdate(new UpdateArgument("delete from metrics where lt_test_set_id is null and date < ?", new Object[]{Instant.now().minus(1, ChronoUnit.DAYS)}, getDataSourceId())).setHandler(ar -> {
                if (ar.failed())
                    Logger.log("Deleting metrics in database failed!", ar.cause());
                else
                    Logger.log("" + ar.result().getRowCount() + " metrics records have been deleted from the database");
            }));
    }

    @Override
    public void onStop() {
        if (metricsCapturePeriodicTimer != null) {
            Logger.log("Stopping system metrics recorder activity...");
            metricsCapturePeriodicTimer.cancel();
            metricsCapturePeriodicTimer = null;
            metricsCleaningPeriodicTimer.cancel();
            metricsCleaningPeriodicTimer = null;
        }
    }

    // Static method helper to start this activity

    public static void startAsServerActivity() {
        ActivityManager.startAsServerActivity(new SystemMetricsRecorderActivity(), DomainActivityContext.createDomainActivityContext(DomainModelSnapshotLoader.getDataSourceModel()));
    }

}