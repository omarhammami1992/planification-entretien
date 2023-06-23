package com.soat.planification_entretien.archi_hexa.infrastructure.db.repository;

import com.soat.planification_entretien.archi_hexa.infrastructure.db.model.DbRecruteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruteurRepository extends JpaRepository<DbRecruteur, Integer> {

    List<DbRecruteur> findAllByExperienceInYearsGreaterThan(Integer experience);
}
