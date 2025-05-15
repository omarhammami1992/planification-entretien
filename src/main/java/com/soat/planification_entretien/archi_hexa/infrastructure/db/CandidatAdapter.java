package com.soat.planification_entretien.archi_hexa.infrastructure.db;

import com.soat.planification_entretien.archi_hexa.domain.entity.Candidat;
import com.soat.planification_entretien.archi_hexa.domain.port.CandidatPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.entity.DBCandidat;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.repository.CandidatRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CandidatAdapter implements CandidatPort {

    private final CandidatRepository candidatRepository;

    public CandidatAdapter(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    @Override
    public Integer save(Candidat candidat) {
        DBCandidat Candidat = new DBCandidat(
                candidat.getLanguage(),
                candidat.getEmail(),
                candidat.getExperienceEnAnnees()
        );
        DBCandidat savedCandidat = candidatRepository.save(Candidat);
        return savedCandidat.getId();
    }
}
