package tian.record;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class RecordApplicationLifeCycle {

    @Inject
    RecordService service;

    private static final Logger LOGGER = Logger.getLogger(RecordApplicationLifeCycle.class);

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("The Record application is starting with profile " + ProfileManager.getActiveProfile());
        service.initialRecords();

    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The Record application is stopping...");
    }

}
