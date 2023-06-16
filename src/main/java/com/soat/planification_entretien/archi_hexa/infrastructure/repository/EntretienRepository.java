package com.soat.planification_entretien.archi_hexa.infrastructure.repository;

import java.util.List;

import com.soat.planification_entretien.archi_hexa.infrastructure.model.DbCandidat;
import com.soat.planification_entretien.archi_hexa.infrastructure.model.DbEntretien;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntretienRepository extends CrudRepository<DbEntretien, Integer> {
    DbEntretien findByCandidat(DbCandidat candidat);

    DbEntretien save(DbEntretien entretien);

    List<DbEntretien> findAll();
}
