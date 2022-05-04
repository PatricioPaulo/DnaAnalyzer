package com.mercadolibre.adnmutant.repositories;

import com.mercadolibre.adnmutant.models.DnaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MutantRepository extends JpaRepository<DnaModel, Long> {
}
