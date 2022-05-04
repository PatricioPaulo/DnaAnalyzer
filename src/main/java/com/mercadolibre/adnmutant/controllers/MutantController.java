package com.mercadolibre.adnmutant.controllers;

import com.mercadolibre.adnmutant.models.DnaModel;
import com.mercadolibre.adnmutant.models.DnaRatioModel;
import com.mercadolibre.adnmutant.models.ResDnaModel;
import com.mercadolibre.adnmutant.sercives.MutantService;
import com.mercadolibre.adnmutant.util.DnaSplitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api")
public class MutantController {
    
    @Autowired
    MutantService mutantService;

    @GetMapping(value="/health")
    public ResponseEntity<ResDnaModel> isMutant() {
        String[] aux = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        ResDnaModel dna = new ResDnaModel();
        dna.setDna(aux);
        return ResponseEntity.ok(dna);
    }

    @PostMapping(value="/mutant")
    public ResponseEntity<?> dnaAnalyzer (@RequestBody ResDnaModel resDna) {
        try {
            DnaModel dna = new DnaModel();
            dna.setSequence(DnaSplitter.composeDna(resDna.getDna()));
            dna = mutantService.saveDna(dna);
            if (dna.isMutant()) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value="/stats")
    public ResponseEntity mutantRatio () {
        DnaRatioModel ratio = mutantService.dnaRatio();
        return ResponseEntity.ok(ratio);
    }
}
