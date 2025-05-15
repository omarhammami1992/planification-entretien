package com.soat.planification_entretien.archi_hexa.infrastructure.db.repository;

import java.util.List;

import com.soat.planification_entretien.archi_hexa.infrastructure.db.entity.DBCandidat;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.entity.DBEntretien;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntretienRepository extends CrudRepository<DBEntretien, Integer> {
    DBEntretien findByCandidat(DBCandidat candidat);

    DBEntretien save(DBEntretien entretien);

    List<DBEntretien> findAll();
}
