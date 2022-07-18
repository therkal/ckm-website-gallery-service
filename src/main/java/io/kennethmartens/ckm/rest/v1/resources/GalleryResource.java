package io.kennethmartens.ckm.rest.v1.resources;

import io.kennethmartens.ckm.service.GalleryServiceImpl;
import io.kennethmartens.ckm.entities.Gallery;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Slf4j
@Path(GalleryResource.API_GALLERIES)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GalleryResource {
    public static final String API_GALLERIES = "/gallery";
    private final GalleryServiceImpl service;

    public GalleryResource(GalleryServiceImpl service) {
        this.service = service;
    }

    @GET
    public Uni<List<Gallery>> get() {
        log.info("GET request to {}", API_GALLERIES);
        return service.get();
    }

    @Path("{id}")
    @GET
    public Uni<Gallery> findById(@PathParam("id") String id) {
        log.info("GET request to {}/{}", API_GALLERIES, id);
        return service.findById(id);
    }

    @POST
    public Uni<Gallery> CreateGallery(Gallery gallery) {
        log.info("POST request to {} with {}", API_GALLERIES, gallery);
        return service.persist(gallery);
    }

    @Path("{id}")
    @PUT
    public Uni<Gallery> update(@PathParam("id") String id, Gallery gallery) {
        log.info("PUT request to {}/{} and body {}", API_GALLERIES, id, gallery);

        return service.update(id, gallery);
    }

    @Path("{id}")
    @DELETE
    public Uni<Object> delete(@PathParam("id") String id) {
        log.info("DELETE request to {}/{}", API_GALLERIES, id);
        return service.delete(id);
    }
}
