package com.soat.planification_entretien.archi_hexa.infrastructure.db.repository;

import com.soat.planification_entretien.archi_hexa.infrastructure.db.model.JpaCandidat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatRepository extends JpaRepository<JpaCandidat, Integer> {
}
