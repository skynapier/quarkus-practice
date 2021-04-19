package tian.timeconverter;

import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

@Path("/api/time-converter")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecordResource {

    @Inject
    RecordService service;

    private static final Logger LOGGER = Logger.getLogger(RecordResource.class);


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        Record rec = new Record();

        rec.id = "2";

        rec.test = "test";
        service.persistRecord(rec);

        return "Hello RESTEasy";
    }



}

