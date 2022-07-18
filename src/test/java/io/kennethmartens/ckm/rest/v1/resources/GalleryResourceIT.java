package io.kennethmartens.ckm.rest.v1.resources;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusIntegrationTest
public class GalleryResourceIT extends GalleryResourceTest {
    // Execute the same tests but in native mode.
}
