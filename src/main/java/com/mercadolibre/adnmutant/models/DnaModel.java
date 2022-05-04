package com.mercadolibre.adnmutant.models;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name ="dna")
@Data
public class DnaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;

    @Column(unique = true, nullable = false)
    private String sequence;

    private boolean isMutant;
}
