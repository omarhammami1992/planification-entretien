package com.soat.planification_entretien.archi_hexa.infrastructure.database.repository;

import com.soat.planification_entretien.archi_hexa.infrastructure.database.entity.RecruteurEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruteurRepository extends JpaRepository<RecruteurEntity, Integer> {
}
