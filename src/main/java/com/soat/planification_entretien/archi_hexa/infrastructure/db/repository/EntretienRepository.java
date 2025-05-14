package com.soat.planification_entretien.archi_hexa.infrastructure.db.repository;

import java.util.List;

import com.soat.planification_entretien.archi_hexa.infrastructure.db.entity.Candidat;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.entity.Entretien;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntretienRepository extends CrudRepository<Entretien, Integer> {
    Entretien findByCandidat(Candidat candidat);

    Entretien save(Entretien entretien);

    List<Entretien> findAll();
}
