package io.kennethmartens.ckm.service;

import io.kennethmartens.ckm.entities.Gallery;
import io.kennethmartens.ckm.repositories.GalleryRepository;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.List;

@Slf4j
@ApplicationScoped
public class GalleryServiceImpl implements DataService<Gallery> {

    private final GalleryRepository repository;

    GalleryServiceImpl(GalleryRepository repository) {
        this.repository = repository;
    }

    public Uni<List<Gallery>> get() {
        return repository.listAll();
    }

    public Uni<Gallery> findById(String id) {
        return repository.findById(id)
                // If the item cannot be found, fail
                .onItem().ifNull().fail()
                // Throw exception on failure.
                .onFailure()
                .transform(t -> new NotFoundException(String.format("Gallery with id %1$s not found", id)));
    }

    public Uni<Gallery> persist(Gallery gallery) {
        return repository.persistAndFlush(gallery)
                // ToDo: Check what persistence exceptions are thrown
                // Catch Persistence Exception
                .onFailure(PersistenceException.class)
                .transform(t ->
                        new EntityExistsException(String.format("Gallery with title %1$s exists", gallery.getTitle()))
                );
    }

    public Uni<Gallery> update(String id, Gallery gallery) {
        if(gallery.getTitle() == null || gallery.getTitle().equals("")) {
            throw new BadRequestException("Property title must be set.");
        }

        return findById(id)
                .map(g -> {
                    g.setTitle(gallery.getTitle());

                    return g;
                })
                .flatMap(this::persist);
    }

    public Uni<Object> delete(String id) {
        return findById(id).flatMap(repository::delete);
    }
}
