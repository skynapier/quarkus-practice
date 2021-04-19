package tian.timeconverter;



import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.persistence.*;


@Schema(description = "Record representation")
@Entity
public class Record extends PanacheEntityBase {

    @Id
//    @SequenceGenerator(
//            name = "personSequence",
//            sequenceName = "person_id_seq",
//            allocationSize = 1,
//            initialValue = 4)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personSequence")
    public String id;

    @Schema(required = true)
    public String test;


}
