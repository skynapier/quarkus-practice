package tian;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;
import org.jboss.logging.Logger;
import tian.record.RecordApplicationLifeCycle;
import tian.record.RecordService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class TechnicalAssessmentApplicationLifeCycle {
    @Inject
    RecordService service;

    private static final Logger LOGGER = Logger.getLogger(TechnicalAssessmentApplicationLifeCycle.class);

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("The Record application is starting with profile " + ProfileManager.getActiveProfile());
        service.initialRecords();

    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The Record application is stopping...");
    }
}
