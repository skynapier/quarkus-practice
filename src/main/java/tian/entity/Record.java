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
import java.time.Instant;


@Schema(description = "Record representation")
@JsonIgnoreProperties( { "timeStamp" })
@Entity
public class Record extends PanacheEntityBase {

    @Id
    @JsonbProperty("ID")
    public String id;

    @Schema(required = true)
    @JsonbProperty("Latitude")
    public Float lat;

    @Schema(required = true)
    @JsonbProperty("Longitude")
    public Float lng;

    @JsonIgnore
    @JsonbProperty("TIME")
    @Schema(required = true)
    public Long timeStamp;

    @JsonInclude
    @JsonbProperty("TIME")
    @Transient
    @Schema(hidden = true)
    public String timeStampConverted;

}
