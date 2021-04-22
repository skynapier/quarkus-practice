package tian.timeconverter;


import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;
import tian.record.entity.Record;
import tian.record.entity.RecordJSON;
import tian.timeconverter.entity.TimeConverter;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.time.Instant;



@Path("/api/time-converter")
@Tag(name = "Time Converter Endpoint")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TimeConverterResource {
    @Inject
    TimeConverterService timeConverterService;

    private static final Logger LOGGER = Logger.getLogger(TimeConverterResource.class);


    @GET
    @Path("/via-http")
    @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType
            .APPLICATION_JSON ))
    @APIResponse(responseCode = "406", description = "Wrong Input ")
    public Response convertViaHttp(@NotNull @QueryParam("timestamp") Long timeStamp,
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

    @GET
    @Path("/via-json")
    @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType
            .APPLICATION_JSON, schema = @Schema(implementation = TimeConverter.class, type = SchemaType
            .ARRAY) ))
    @APIResponse(responseCode = "406", description = "Wrong Input ")
    public Response convertViaJson(@RequestBody(required = true, content = @Content(mediaType = MediaType
            .APPLICATION_JSON, schema = @Schema(implementation = TimeConverter.class)))
                                       @Valid TimeConverter timeConverter, @Context UriInfo uriInfo
    ){

        if(timeConverter != null){
            String timeStampConverted = timeConverterService.timeParser(timeConverter.timeStamp
                    ,timeConverter.lat ,timeConverter.lng);

            String response = String.format("{\"time\": \"%s\" }", timeStampConverted);

            return Response.status(200).entity(response).type(MediaType.APPLICATION_JSON)
                    .build();

        }

        return Response.status(Response.Status.NOT_ACCEPTABLE).build();

    }

}
