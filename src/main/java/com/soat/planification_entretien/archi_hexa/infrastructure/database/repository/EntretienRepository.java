package com.soat.planification_entretien.archi_hexa.infrastructure.database.repository;

import java.util.List;

import com.soat.planification_entretien.archi_hexa.infrastructure.database.entity.CandidatEntity;
import com.soat.planification_entretien.archi_hexa.infrastructure.database.entity.EntretienEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntretienRepository extends CrudRepository<EntretienEntity, Integer> {
    EntretienEntity findByCandidat(CandidatEntity candidat);

    EntretienEntity save(EntretienEntity entretien);

    List<EntretienEntity> findAll();
}
