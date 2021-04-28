package tian.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;


@Schema(description = "Record representation")
@JsonIgnoreProperties( { "timeStamp" })
@Entity
public class Record extends PanacheEntityBase {

    @Id
    @JsonbProperty("ID")
    @NotNull(message = "Please enter ID")
    public String id;

    @Schema(required = true)
    @JsonbProperty("Latitude")
    @NotNull(message = "Please enter Latitude")
    public Float lat;

    @Schema(required = true)
    @NotNull(message = "Please enter Longitude")
    @JsonbProperty("Longitude")
    public Float lng;

//    @JsonIgnore
//    @JsonbProperty("TIME")
    @NotNull(message = "Please enter timestamp")
    @Schema(required = true)
    public Long timeStamp;

    @JsonInclude
    @JsonbProperty("TIME")
    @Transient
    @Schema(hidden = true)
    public String timeStampConverted;

}
