package io.kennethmartens.ckm.repositories;

import io.kennethmartens.ckm.entities.Gallery;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GalleryRepository implements PanacheRepositoryBase<Gallery, String> {
}
