package tian.mockservice;


import io.quarkus.test.Mock;
import org.jboss.logging.Logger;
import tian.timeconverter.TimeConverterResource;
import tian.timeconverter.TimeConverterService;

import javax.enterprise.context.ApplicationScoped;

@Mock
@ApplicationScoped
public class MockTimeConverterService extends TimeConverterService{

    private static final Long DEFAULT_TIMESTAMP = 1492301050L;
    private static final Float DEFAULT_LAT = 33.865143f;
    private static final Float DEFAULT_LNG = 151.2099f;
    private static final String DEFAULT_TIME = "16/04/2017 10:04 GMT+10:00 +1000";
    private static final Logger LOGGER = Logger.getLogger(TimeConverterResource.class);

    @Override
    public String timeParser(Long timeStamp, float lat, float lng){
        if (DEFAULT_LAT.equals(lat) && DEFAULT_LNG.equals(lng) && DEFAULT_TIMESTAMP.equals(timeStamp)){
            LOGGER.info("confirm: " + lng + " " + lat + " "+ timeStamp);
            return  DEFAULT_TIME;
        }else{
            return "MockTimeConverterReturn";
        }

    }
}
