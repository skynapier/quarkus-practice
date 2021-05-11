package tian.websockets;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;
import org.jboss.logging.Logger;
import tian.entity.Record;
import tian.record.RecordApplicationLifeCycle;
import tian.record.RecordService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.List;


@ApplicationScoped
public class ChatApplicationLifeCycle {


    private static final Logger LOGGER = Logger.getLogger(ChatApplicationLifeCycle.class);

    void onStart(@Observes StartupEvent ev) {

        LOGGER.info("The Chat application is starting with profile " + ProfileManager.getActiveProfile());


    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The Chat application is stopping...");
    }
}
