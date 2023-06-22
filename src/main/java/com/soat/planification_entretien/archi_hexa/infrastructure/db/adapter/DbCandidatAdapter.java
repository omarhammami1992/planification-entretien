package com.soat.planification_entretien.archi_hexa.infrastructure.db.adapter;

import com.soat.planification_entretien.archi_hexa.domain.entity.Candidat;
import com.soat.planification_entretien.archi_hexa.domain.port.CandidatPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.model.DbCandidat;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.repository.CandidatRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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

    @Override
    public Optional<Candidat> findById(Integer id) {
        final Optional<DbCandidat> optionalDbCandidat = candidatRepository.findById(id);
        return optionalDbCandidat.map(DbCandidatAdapter::toCandidat);
    }

    private static Candidat toCandidat(DbCandidat dbCandidat) {
        return new Candidat(dbCandidat.getId(), dbCandidat.getLanguage(), dbCandidat.getEmail(), dbCandidat.getExperienceInYears());
    }

    private static DbCandidat toDbCandidat(Candidat candidat) {
        return new DbCandidat(candidat.language(), candidat.email(), candidat.experienceEnAnnees());
    }
}
