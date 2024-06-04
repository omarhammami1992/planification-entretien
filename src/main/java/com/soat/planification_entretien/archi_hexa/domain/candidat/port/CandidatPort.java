package com.soat.planification_entretien.archi_hexa.domain.candidat.port;

import com.soat.planification_entretien.archi_hexa.domain.candidat.entity.Candidat;

public interface CandidatPort {
    int save(Candidat candidat);
}
