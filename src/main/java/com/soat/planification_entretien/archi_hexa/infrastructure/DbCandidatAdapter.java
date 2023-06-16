package com.soat.planification_entretien.archi_hexa.infrastructure;

import com.soat.planification_entretien.archi_hexa.domain.entity.Candidat;
import com.soat.planification_entretien.archi_hexa.domain.port.CandidatPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.model.DbCandidat;
import com.soat.planification_entretien.archi_hexa.infrastructure.repository.CandidatRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DbCandidatAdapter implements CandidatPort {

    private final CandidatRepository candidatRepository;

    public DbCandidatAdapter(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    @Override
    public Integer save(Candidat candidat) {
        final DbCandidat dbCandidat = toDbCandidat(candidat);
        final DbCandidat savedDbCandidat = candidatRepository.save(dbCandidat);
        return savedDbCandidat.getId();
    }

    private static DbCandidat toDbCandidat(Candidat candidat) {
        return new DbCandidat(candidat.language(), candidat.email(), candidat.experienceEnAnnees());
    }
}
