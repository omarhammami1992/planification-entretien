package com.soat.planification_entretien.archi_hexa.infrastructure.database.adapter;

import com.soat.planification_entretien.archi_hexa.domain.candidat.entity.Candidat;
import com.soat.planification_entretien.archi_hexa.domain.candidat.port.CandidatPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.database.entity.CandidatEntity;
import com.soat.planification_entretien.archi_hexa.infrastructure.database.repository.CandidatRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CandidatAdapter implements CandidatPort {
    private final CandidatRepository candidatRepository;

    public CandidatAdapter(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    @Override
    public int save(Candidat candidat) {
        final CandidatEntity candidatEntity = new CandidatEntity(candidat.getLanguage(), candidat.getEmail(), candidat.getExperienceEnAnnees());
        final CandidatEntity saveCandidatEntity = candidatRepository.save(candidatEntity);
        return saveCandidatEntity.getId();
    }
}
