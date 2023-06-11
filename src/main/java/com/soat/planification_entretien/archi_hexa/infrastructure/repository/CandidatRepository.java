package com.soat.planification_entretien.archi_hexa.infrastructure.repository;

import com.soat.planification_entretien.archi_hexa.infrastructure.model.Candidat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatRepository extends JpaRepository<Candidat, Integer> {
}