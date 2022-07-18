package io.kennethmartens.ckm.rest.v1.resources;

import io.kennethmartens.ckm.entities.Gallery;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class GalleryResourceTest {

    static final String BASE_PATH = "/api/v1";

    protected Gallery createdGallery;

    @Test
    public void testGetEndpoint() {
        given()
                .when().get(this.getApiPath())
                .then()
                .statusCode(200)
                .body(is("[]"));
    }

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

        createdGallery = returned;
    }

    @Test
    public void shouldGetSpecificGallery() {
        given().when()
                .get(getApiPath() + "/" + createdGallery.getId())
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(is(createdGallery));
    }

    @Test
    public void shouldDeleteGallery() {
        given().when()
                .delete(getApiPath() + "/" + createdGallery.getId())
                .then()
                .statusCode(204);
    }

    private String getApiPath() {
        return BASE_PATH + GalleryResource.API_GALLERIES;
    }

}
