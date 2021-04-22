package tian.record;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;
import tian.record.entity.Record;
import tian.record.entity.RecordJSON;
import tian.timeconverter.TimeConverterService;


import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.*;

@Path("/api/record")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Record Endpoint")
public class RecordResource {

    @Inject
    TimeConverterService timeConverterService;

    @Inject
    RecordService recordService;

    private static final Logger LOGGER = Logger.getLogger(RecordResource.class);


    @GET
    @Path("/all")
    @Operation(summary = "Returns all Records")
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
            String timeStampString = timeConverterService.timeParser(record.timeStamp, lat, lng);
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
    @APIResponse(responseCode = "404", description = "The record is not found for the given identifier")
    public Response getRecord(@Parameter(description = "Record identifier", required = true) @PathParam("id") String id ) {
        Optional<Record> record = recordService.findRecordById(id);

        if(record.isPresent()){
            LOGGER.debug("Found record " + record);

            float lat = record.get().lat;
            float lng = record.get().lng;
            String timeStampString = timeConverterService.timeParser(record.get().timeStamp, lat, lng);
            RecordJSON recordJSON = new RecordJSON(id, lat, lng, timeStampString);

            return Response.ok(recordJSON).build();
        } else{
            LOGGER.debug("No record found with id " + id);
            return Response.status(NOT_FOUND).build();
        }

    }

    @POST
    @Operation(summary = "Create a Valid Record")
    @APIResponse(responseCode = "201", content = @Content(mediaType = MediaType
            .APPLICATION_JSON, schema = @Schema(implementation = Record.class, type = SchemaType
            .ARRAY)))
    @APIResponse(responseCode = "400", description = "The record for the given identifier is already exist")
    public Response createRecord(@RequestBody(required = true, content = @Content(mediaType = MediaType
            .APPLICATION_JSON, schema = @Schema(implementation = Record.class)))
                                     @Valid Record record, @Context UriInfo uriInfo){
        Optional<Record> recordExist = recordService.findRecordById(record.id);

        if(recordExist.isPresent()){
            return Response.status(BAD_REQUEST).build();
        }else{
            record = recordService.persistRecord(record);
            UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(record.id);
            LOGGER.debug("New record created with URI " + builder.build().toString());

            return Response.ok(record).build();
        }

    }

    @PUT
    @Operation(summary = "Update an Valid Record")
    @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType
            .APPLICATION_JSON, schema = @Schema(implementation = Record.class, type = SchemaType
            .ARRAY)))
    @APIResponse(responseCode = "404", description = "The record is not found for the given identifier")
    public Response updateRecord(@RequestBody(required = true, content = @Content(mediaType = MediaType
            .APPLICATION_JSON, schema = @Schema(implementation = Record.class)))
                                 @Valid Record record, @Context UriInfo uriInfo){

        Optional<Record> recordExist = recordService.findRecordById(record.id);

        if(recordExist.isPresent()){
            record = recordService.updateRecord(record);
            LOGGER.debug("New record created with URI " + record);
            return Response.ok(record).build();
        }else{
            return Response.status(NOT_FOUND).build();
        }


    }

    @DELETE
    @APIResponse(responseCode = "204", description = "The record has been successfully deleted")
    @APIResponse(responseCode = "404", description = "The record is not found for the given identifier")
    @Path("/{id}")
    @Operation(summary = "Deletes an existing book")
    public  Response deleteRecord(@Parameter(description = "Record identifier"
            ,required = true) @PathParam("id") String id ){

        Optional<Record> recordExist = recordService.findRecordById(id);

        if(recordExist.isPresent()){
            recordService.deleteRecord(id);
            LOGGER.debug("Record Delete with " + id);
            return Response.noContent().build();

        }else{
            return Response.status(NOT_FOUND).build();

        }

    }


}

