package com.soat.planification_entretien.archi_hexa.infrastructure.repository;

import java.util.List;

import com.soat.planification_entretien.archi_hexa.infrastructure.model.Candidat;
import com.soat.planification_entretien.archi_hexa.infrastructure.model.Entretien;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntretienRepository extends CrudRepository<Entretien, Integer> {
    Entretien findByCandidat(Candidat candidat);

    Entretien save(Entretien entretien);

    List<Entretien> findAll();
}