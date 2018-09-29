package webfx.fxkit.javafx.launcher;

import com.sun.javafx.application.ParametersImpl;
import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.application.Application;
import javafx.stage.Stage;
import webfx.fxkits.core.launcher.spi.impl.FxKitLauncherProviderBase;
import webfx.platforms.core.util.function.Factory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bruno Salmon
 */
public final class JavaFxFxKitLauncherProvider extends FxKitLauncherProviderBase {

    private static List<Runnable> readyRunnables = new ArrayList<>();
    private static Factory<Application> applicationFactory;

    @Override
    public String getUserAgent() {
        return "JavaFx";
    }

    @Override
    public void launchApplication(Factory<Application> applicationFactory, String... args) {
        JavaFxFxKitLauncherProvider.applicationFactory = applicationFactory;
        new Thread(() -> {
            Application.launch(FxKitWrapperApplication.class, args);
            System.exit(0);
        }).start();
    }

    private static void onJavaFxToolkitReady() {
        // Activating SVG support
        SvgImageLoaderFactory.install();
        executeReadyRunnables();
    }

    @Override
    public boolean isReady() {
        return readyRunnables == null;
    }

    @Override
    public void onReady(Runnable runnable) {
        synchronized (JavaFxFxKitLauncherProvider.class) {
            if (readyRunnables != null)
                readyRunnables.add(runnable);
            else
                super.onReady(runnable);
        }
    }

    private static void executeReadyRunnables() {
        synchronized (JavaFxFxKitLauncherProvider.class) {
            if (readyRunnables != null) {
                List<Runnable> runnables = readyRunnables;
                readyRunnables = null;
                //runnables.forEach(Runnable::run); doesn't work on Android
                for (Runnable runnable : runnables)
                    runnable.run();
            }
        }
    }

    public static class FxKitWrapperApplication extends Application {

        Application application;

        @Override
        public void init() throws Exception {
            if (applicationFactory != null)
                application = applicationFactory.create();
            if (application != null) {
                ParametersImpl.registerParameters(application, getParameters());
                application.init();
            }
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            onJavaFxToolkitReady();
            if (application != null)
                application.start(primaryStage);
        }

    }
}