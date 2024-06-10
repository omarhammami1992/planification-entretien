package com.soat.planification_entretien.archi_hexa.infrastructure.database.adapter;

import com.soat.planification_entretien.archi_hexa.domain.entity.Candidat;
import com.soat.planification_entretien.archi_hexa.domain.port.CandidatPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.database.entity.CandidatEntity;
import com.soat.planification_entretien.archi_hexa.infrastructure.database.repository.CandidatRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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

    @Override
    public Optional<Candidat> findById(int candidatId) {
        final Optional<CandidatEntity> optionalCandidatEntity = candidatRepository.findById(candidatId);
        return optionalCandidatEntity.map(candidatEntity -> new Candidat(candidatEntity.getId(), candidatEntity.getLanguage(), candidatEntity.getEmail(), candidatEntity.getExperienceInYears()));
    }
}
