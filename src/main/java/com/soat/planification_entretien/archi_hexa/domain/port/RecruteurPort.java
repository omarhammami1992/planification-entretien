package com.soat.planification_entretien.archi_hexa.domain.port;

import com.soat.planification_entretien.archi_hexa.domain.entity.Recruteur;

import java.util.Optional;

public interface RecruteurPort {

    Integer save(Recruteur recruteur);

    Optional<Recruteur> findById(int recruteurId);
}
