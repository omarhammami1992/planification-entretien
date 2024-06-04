package com.soat.planification_entretien.archi_hexa.domain.candidat.use_case;

import com.soat.planification_entretien.archi_hexa.domain.candidat.entity.Candidat;
import com.soat.planification_entretien.archi_hexa.domain.candidat.port.CandidatPort;
import org.springframework.stereotype.Service;

@Service
public class CreerCandidat {

    private final CandidatPort candidatPort;

    public CreerCandidat(CandidatPort candidatPort) {
        this.candidatPort = candidatPort;
    }

    public int execute(String language, String email, int experienceEnAnnees) {
        final Candidat candidat = new Candidat(language, email, experienceEnAnnees);
        return candidatPort.save(candidat);
    }

}
