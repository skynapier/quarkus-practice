package tian.record.entity;


import javax.json.bind.annotation.JsonbProperty;


public class RecordJSON {

    @JsonbProperty("ID")
    public String id;

    @JsonbProperty("Latitude")
    public Float lat;

    @JsonbProperty("Longitude")
    public Float lng;

    @JsonbProperty("TIME")
    public String timeStampString;

    public RecordJSON(String id, Float lat, Float lng, String timeStampString){
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.timeStampString = timeStampString;
    }


}
