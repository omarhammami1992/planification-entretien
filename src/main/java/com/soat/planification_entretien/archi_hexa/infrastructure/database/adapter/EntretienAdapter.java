package com.soat.planification_entretien.archi_hexa.infrastructure.database.adapter;

import com.soat.planification_entretien.archi_hexa.domain.entity.Candidat;
import com.soat.planification_entretien.archi_hexa.domain.entity.Entretien;
import com.soat.planification_entretien.archi_hexa.domain.entity.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.port.EntretienPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.database.entity.CandidatEntity;
import com.soat.planification_entretien.archi_hexa.infrastructure.database.entity.EntretienEntity;
import com.soat.planification_entretien.archi_hexa.infrastructure.database.entity.RecruteurEntity;
import com.soat.planification_entretien.archi_hexa.infrastructure.database.repository.EntretienRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EntretienAdapter implements EntretienPort {
    private final EntretienRepository entretienRepository;

    public EntretienAdapter(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    @Override
    public void save(Entretien entretien) {
        final Candidat candidat = entretien.getCandidat();
        final CandidatEntity candidatEntity = new CandidatEntity(candidat.getId(), candidat.getLanguage(), candidat.getEmail(), candidat.getExperienceEnAnnees());

        final Recruteur recruteur = entretien.getRecruteur();
        final RecruteurEntity recruteurEntity = new RecruteurEntity(recruteur.getId(), recruteur.getLanguage(), recruteur.getEmail(), recruteur.getExperienceEnAnnees());

        final EntretienEntity entretienEntity = EntretienEntity.of(candidatEntity, recruteurEntity, entretien.getHoraireEntretien());
        entretienRepository.save(entretienEntity);
    }

    @Override
    public List<Entretien> findAll() {
        final List<EntretienEntity> entretienEntities = entretienRepository.findAll();
        return entretienEntities.stream()
                .map(EntretienAdapter::toDomain)
                .toList();
    }

    private static Entretien toDomain(EntretienEntity entretienEntity) {
        final CandidatEntity candidatEntity = entretienEntity.getCandidat();
        Candidat candidat = new Candidat(candidatEntity.getLanguage(), candidatEntity.getEmail(), candidatEntity.getExperienceInYears());

        final RecruteurEntity recruteurEntity = entretienEntity.getRecruteur();
        Recruteur recruteur = new Recruteur(recruteurEntity.getLanguage(), recruteurEntity.getEmail(), recruteurEntity.getExperienceInYears());

        return new Entretien(entretienEntity.getId(), candidat, recruteur, entretienEntity.getHoraireEntretien());
    }
}
