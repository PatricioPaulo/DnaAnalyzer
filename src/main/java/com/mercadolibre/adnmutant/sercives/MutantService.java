package com.mercadolibre.adnmutant.sercives;

import com.mercadolibre.adnmutant.models.DnaModel;
import com.mercadolibre.adnmutant.models.DnaRatioModel;
import com.mercadolibre.adnmutant.repositories.MutantRepository;
import com.mercadolibre.adnmutant.util.DnaSplitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class MutantService {

    @Autowired
    MutantRepository mutantRepository;

    public DnaModel saveDna(DnaModel dna) throws Exception {
        validateDnaChain(DnaSplitter.dnaSplitter(dna.getSequence()));
        dna.setSequence(dna.getSequence());
        dna.setMutant(isMutant(DnaSplitter.dnaSplitter(dna.getSequence())));
        return mutantRepository.save(dna);
    }

    public boolean isMutant (String[] dna) {
        int count_seq= 0;
        count_seq += horizontalSearch(dna);
        count_seq += verticalSearch(dna);
        count_seq += diagonalSearch(dna);
        return count_seq > 1;
    }

    private void validateDnaChain(String[] dna) throws Exception {
        int countElements = dna.length;
        for(String d : dna) {
            if(d.length()!= countElements){
                throw new Exception("Dna chain incomplete");
            }
        }
    }

    private int horizontalSearch(String[] dna){
        int sequenceFinded=0;
        for (String d: dna) {
            if(patternCompare(d)) {
                sequenceFinded++;
            }
        }
        return sequenceFinded;
    }

    private int verticalSearch(String[] dna){
        int sequenceFinded=0;
        for (int i=0; i<dna.length; i++){
            StringBuilder dnaSequence = new StringBuilder();
            for (String s : dna) {
                dnaSequence.append(s.charAt(i));
            }
            if(patternCompare(dnaSequence.toString())){
                sequenceFinded++;
            }
        }
        return sequenceFinded;
    }

    private int diagonalSearch(String[] dna){
        int sequenceFinded=0;
        int len = dna.length;
        for(int diag = 1-len; diag <= len-1; diag++){
            StringBuilder dnaSequence = new StringBuilder();
            for(int vert = Math.max(0,diag), hor = -Math.min(0,diag);
                vert< len && hor <len;
                vert++, hor++
            ){
                dnaSequence.append(dna[vert].charAt(hor));
            }
            if (patternCompare(dnaSequence.toString())) {
                sequenceFinded++;
            }
        }
        return sequenceFinded;
    }

    private boolean patternCompare(String dnaSequence) {
        final String regex = "(.)\\1{3}";
        final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(dnaSequence).find();
    }

    public DnaRatioModel dnaRatio(){
        List<DnaModel> allDna = mutantRepository.findAll();
        float  mutant = allDna.stream().filter(DnaModel::isMutant).count();
        float human = allDna.stream().filter(h -> !h.isMutant()).count();
        DnaRatioModel dnaRatio = new DnaRatioModel();
        dnaRatio.setCount_human_dna(human);
        dnaRatio.setCount_mutant_dna(mutant);
        if(human!= 0) dnaRatio.setRatio(mutant/human);
        else dnaRatio.setRatio(0);
        return dnaRatio;
    }
}
