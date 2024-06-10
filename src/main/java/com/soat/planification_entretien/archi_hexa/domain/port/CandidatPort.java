package com.soat.planification_entretien.archi_hexa.domain.port;

import com.soat.planification_entretien.archi_hexa.domain.entity.Candidat;

import java.util.Optional;

public interface CandidatPort {
    int save(Candidat candidat);

    Optional<Candidat> findById(int candidatId);
}
