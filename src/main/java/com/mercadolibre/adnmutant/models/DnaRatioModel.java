package com.mercadolibre.adnmutant.models;

import lombok.Data;

@Data
public class DnaRatioModel {

    private float count_mutant_dna;
    private float count_human_dna;
    private float ratio;
}
