package tian.record;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;
import tian.record.entity.Record;
import tian.record.entity.RecordJSON;
import tian.timeconverter.TimeConverterService;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/api/record")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecordResource {

    @Inject
    TimeConverterService timeConverterService;

    @Inject
    RecordService recordService;

    private static final Logger LOGGER = Logger.getLogger(RecordResource.class);


    @GET
    @Path("/all")
    @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType
            .APPLICATION_JSON, schema = @Schema(implementation = RecordJSON.class, type = SchemaType
            .ARRAY)))
    @APIResponse(responseCode = "204", description = "No records")
    public Response getAllRecords() {
        List<Record> records = recordService.findAllRecords();

        List<RecordJSON> recordJSONS = new ArrayList<>();

        for(Record record: records){
            String id = record.id;
            float lat = record.lat;
            float lng = record.lng;
            Instant timestamp = record.timstamp;

            String timeStampString = timeConverterService.timeParser(timestamp, lat, lng);
            RecordJSON recordJSON = new RecordJSON(id, lat, lng, timeStampString);

            recordJSONS.add(recordJSON);
        }
        LOGGER.debug("Total number of books " +records);
        return Response.ok(recordJSONS).build();

    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Returns a Record for a given identifier")
    @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType
            .APPLICATION_JSON, schema = @Schema(implementation = RecordJSON.class, type = SchemaType
            .ARRAY)))
    @APIResponse(responseCode = "204", description = "No records")
    public Response getRecord(@Parameter(description = "Record identifier", required = true) @PathParam("id") String id ) {
        Optional<Record> record = recordService.findRecordById(id);

        if(record.isPresent()){
            LOGGER.debug("Found record " + record);

            float lat = record.get().lat;
            float lng = record.get().lng;
            Instant timestamp = record.get().timstamp;

            String timeStampString = timeConverterService.timeParser(timestamp, lat, lng);
            RecordJSON recordJSON = new RecordJSON(id, lat, lng, timeStampString);

            return Response.ok(recordJSON).build();
        } else{
            LOGGER.debug("No record found with id " + id);
            return Response.status(NOT_FOUND).build();
        }


    }




}

