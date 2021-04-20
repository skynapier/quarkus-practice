package tian.record.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;
import java.time.Instant;


@Schema(description = "Record representation")
@Entity
public class Record extends PanacheEntityBase {

    @Id
    @JsonbProperty("ID")
    public String id;

    @Schema(required = true)
    public Float lat;

    @Schema(required = true)
    public Float lng;

    @Schema(required = true)
    public Instant timstamp;




}
