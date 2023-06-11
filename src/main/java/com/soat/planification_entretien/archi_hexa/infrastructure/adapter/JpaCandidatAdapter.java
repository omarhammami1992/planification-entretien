package com.soat.planification_entretien.archi_hexa.infrastructure.adapter;

import com.soat.planification_entretien.archi_hexa.domain.Candidat;
import com.soat.planification_entretien.archi_hexa.domain.port.CandidatPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.model.JpaCandidat;
import com.soat.planification_entretien.archi_hexa.infrastructure.repository.CandidatRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JpaCandidatAdapter implements CandidatPort {
    private final CandidatRepository candidatRepository;

    public JpaCandidatAdapter(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    @Override
    public Integer save(Candidat candidat) {
        JpaCandidat JpaCandidat = new JpaCandidat(candidat.language(), candidat.email(), candidat.experienceEnAnnees());
        JpaCandidat savedCandidat = candidatRepository.save(JpaCandidat);
        return savedCandidat.getId();
    }
}
