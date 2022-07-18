package io.kennethmartens.ckm.rest.v1.resources;

import io.kennethmartens.ckm.entities.Gallery;
import io.kennethmartens.ckm.rest.v1.exception.RestExceptionResponse;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@QuarkusTest
public class GalleryResourceTest {

    static final String BASE_PATH = "/api/v1";

    protected Gallery createdGallery;

    @Order(0)
    @Test
    public void testGetEndpoint() {
        given()
                .when().get(this.getApiPath())
                .then()
                .statusCode(200)
                .body(is("[]"));
    }

    @Order(1)
    @Test
    public void testPostEndpointSuccessfulCreation() {
        String title = "New Gallery";
        Gallery gallery = Gallery.builder()
                .title(title)
                .build();

        Gallery returned = given().when()
                .contentType(ContentType.JSON)
                .body(gallery)
                .post(getApiPath())
                .then()
                .statusCode(200)
                // Get gallery
                .extract()
                .as(Gallery.class);

        assertEquals(title, returned.getTitle());
        assertNotNull(returned.getId());

        this.createdGallery = returned;
    }

    @Order(2)
    @Test
    public void testPostEndpointDuplicateGallery() {
        String title = "New Gallery";
        Gallery gallery = Gallery.builder()
                .title(title)
                .build();

        RestExceptionResponse restExceptionResponse = given().when()
                .contentType(ContentType.JSON)
                .body(gallery)
                .post(getApiPath())
                .then()
                .statusCode(409)
                .extract()
                .as(RestExceptionResponse.class);

        assertNotNull(restExceptionResponse);
        assertNotNull(restExceptionResponse.getTimestamp());
        assertEquals(409, restExceptionResponse.getStatusCode());
        assertEquals(RestResponse.Status.CONFLICT, restExceptionResponse.getStatus());
        assertEquals("Gallery with title New Gallery exists", restExceptionResponse.getMessage());
    }

    private String getApiPath() {
        return BASE_PATH + GalleryResource.API_GALLERIES;
    }

}
