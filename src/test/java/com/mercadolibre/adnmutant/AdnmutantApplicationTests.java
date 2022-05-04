package com.mercadolibre.adnmutant;

import com.mercadolibre.adnmutant.models.DnaModel;
import com.mercadolibre.adnmutant.sercives.MutantService;
import com.mercadolibre.adnmutant.util.DnaSplitter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdnmutantApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testSaveDNA() throws Exception {
		MutantService mutantService = new MutantService();
		String[] squence = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
		DnaModel dnaModel = new DnaModel();
		dnaModel.setSequence(DnaSplitter.composeDna(squence));
		dnaModel = mutantService.saveDna(dnaModel);
		assert dnaModel.isMutant();
	}

	@Test
	void testIsMutant() {
		MutantService mutantService = new MutantService();
		String[] squence1 = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
		String[] squence2 = {"CCCCTA","TCACTG","ATGCGA","CAGTGC","TTATGT","AGAAGG"};
		assert  mutantService.isMutant(squence1);
		assert  mutantService.isMutant(squence2);
	}
}
