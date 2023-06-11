package com.soat.planification_entretien.archi_hexa.infrastructure.adapter;

import com.soat.planification_entretien.archi_hexa.domain.enity.Candidat;
import com.soat.planification_entretien.archi_hexa.domain.port.CandidatPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.model.JpaCandidat;
import com.soat.planification_entretien.archi_hexa.infrastructure.repository.CandidatRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaCandidatAdapter implements CandidatPort {
    private final CandidatRepository candidatRepository;

    public JpaCandidatAdapter(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    @Override
    public Integer save(Candidat candidat) {
        com.soat.planification_entretien.archi_hexa.infrastructure.model.JpaCandidat JpaCandidat = toJpaCandidat(candidat);
        JpaCandidat savedCandidat = candidatRepository.save(JpaCandidat);
        return savedCandidat.getId();
    }

    private static JpaCandidat toJpaCandidat(Candidat candidat) {
        return new JpaCandidat(candidat.language(), candidat.email(), candidat.experienceEnAnnees());
    }

    @Override
    public Optional<Candidat> findById(Integer id) {
        final Optional<JpaCandidat> optionalJpaCandidat = candidatRepository.findById(id);
        return optionalJpaCandidat.map(JpaCandidatAdapter::toCandidat);
    }

    private static Candidat toCandidat(JpaCandidat jpaCandidat) {
        return new Candidat(jpaCandidat.getId(), jpaCandidat.getLanguage(), jpaCandidat.getEmail(), jpaCandidat.getExperienceInYears());
    }
}
