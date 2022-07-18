package io.kennethmartens.ckm.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gallery extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String title;

}
