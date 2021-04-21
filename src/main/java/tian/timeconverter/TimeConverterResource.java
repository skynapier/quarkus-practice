package tian.timeconverter;


import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;
import tian.record.entity.RecordJSON;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;


@Path("/api/time-converter")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TimeConverterResource {
    @Inject
    TimeConverterService timeConverterService;

    private static final Logger LOGGER = Logger.getLogger(TimeConverterResource.class);


    @GET
    @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType
            .APPLICATION_JSON ))
    @APIResponse(responseCode = "406", description = "Wrong Input ")
    public Response allParams(@NotNull @QueryParam("timestamp") Long timeStamp,
                              @NotNull @QueryParam("lat") float lat,
                              @NotNull @QueryParam("lng") float lng){

        if(timeStamp != null){
            String timeStampConverted = timeConverterService.timeParser(timeStamp, lat, lng);

            String response = String.format("{\"time\": \"%s\" }", timeStampConverted);

            return Response.status(200).entity(response).type(MediaType.APPLICATION_JSON)
                    .build();

        }

        return Response.status(Response.Status.NOT_ACCEPTABLE).build();


    }

}
