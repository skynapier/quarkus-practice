package tian.timeconverter;

import net.iakovlev.timeshape.TimeZoneEngine;
import javax.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@ApplicationScoped
public class TimeConverterService {

    public String timeParser(Instant timeStamp, float lat, float lng){
        TimeZoneEngine engine = TimeZoneEngine.initialize();
        Optional<ZoneId> possibleZoneId = engine.query(lat, lng);

        if(possibleZoneId.isPresent()){
            DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm z Z")
                    .withZone( possibleZoneId.get() );

            return DATE_TIME_FORMATTER.format(timeStamp);
        }else{
            return null;
        }

    }

}
