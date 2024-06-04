package com.soat.planification_entretien.archi_hexa.infrastructure.database.repository;

import com.soat.planification_entretien.archi_hexa.infrastructure.database.entity.CandidatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatRepository extends JpaRepository<CandidatEntity, Integer> {
}
