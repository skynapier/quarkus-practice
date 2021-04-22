package tian.timeconverter.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.persistence.Entity;

@Schema(description = "Record representation")
@Entity
public class TimeConverter extends PanacheEntity {

    @Schema(required = true)
    public Float lat;

    @Schema(required = true)
    public Float lng;

    @Schema(required = true)
    public Long timeStamp;
}
