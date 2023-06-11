package com.soat.planification_entretien.archi_hexa.infrastructure.db.repository;

import java.util.List;

import com.soat.planification_entretien.archi_hexa.infrastructure.db.model.JpaEntretien;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.model.JpaCandidat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntretienRepository extends CrudRepository<JpaEntretien, Integer> {
    JpaEntretien findByCandidat(JpaCandidat candidat);

    JpaEntretien save(JpaEntretien entretien);

    List<JpaEntretien> findAll();
}