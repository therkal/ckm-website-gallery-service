package io.kennethmartens.ckm.entities;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@MappedSuperclass
@Data
public class BaseEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    @Column(unique = true)
    protected String id;

}
