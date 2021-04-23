package tian.record;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static javax.ws.rs.core.Response.Status.CREATED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import tian.Database;
import tian.record.entity.Record;


import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.OK;

@QuarkusTest
@QuarkusTestResource(Database.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecordResourceTest {
    private static final String DEFAULT_ID = "0";
    private static final Long DEFAULT_TIMESTAMP = 1492301050L;
    private static final Float DEFAULT_LAT = 33.865143f;
    private static final Float DEFAULT_LNG = 151.2099f;
    private static final String DEFAULT_TIME = "16/04/2017 10:04 GMT+10:00 +1000";


    @Test
    @Order(1)
    void shouldGetInitialItems() {
        given().when()
                .get("/api/record/all")
                .then()
                .statusCode(OK.getStatusCode())
                .body("$.size()", is(11));

    }

    @Test
    @Order(2)
    void shouldGetAnItem() {
        given().when()
                .get("/api/record/0")
                .then()
                .statusCode(OK.getStatusCode())
                .body("ID", equalTo(DEFAULT_ID),
                        "TIME", equalTo(DEFAULT_TIME),
                        "Latitude", equalTo(DEFAULT_LAT),
                        "Longitude", equalTo(DEFAULT_LNG),
                        "size()", is(4));
    }

    @Test
    @Order(3)
    void shouldAddAnItem() {

        Record  rec = new Record();
        rec.id = "test";
        rec.timeStamp = DEFAULT_TIMESTAMP;
        rec.lat = DEFAULT_LAT;
        rec.lng = DEFAULT_LNG;

        given()
                .body(rec)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when()
                .post("/api/record")
                .then()
                .statusCode(CREATED.getStatusCode());

        given().when()
                .get("/api/record/test")
                .then()
                .statusCode(OK.getStatusCode())
                .body("ID", equalTo("test"),
                        "TIME", equalTo(DEFAULT_TIME),
                        "Latitude", equalTo(DEFAULT_LAT),
                        "Longitude", equalTo(DEFAULT_LNG),
                        "size()", is(4));
    }


}
