package com.mercadolibre.adnmutant.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResDnaModel {

    @JsonProperty("dna")
    private String[] dna;

    public ResDnaModel(String[] dna) {
        this.dna = dna;
    }

    public ResDnaModel() {
    }

    public String[] getDna() {
        return dna;
    }

    public void setDna(String[] dna) {
        this.dna = dna;
    }
}
