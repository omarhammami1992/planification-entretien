package com.soat.planification_entretien.archi_hexa.infrastructure.database.adapter;

import com.soat.planification_entretien.archi_hexa.domain.entity.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.port.RecruteurPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.database.entity.RecruteurEntity;
import com.soat.planification_entretien.archi_hexa.infrastructure.database.repository.RecruteurRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RecruteurAdapter implements RecruteurPort {
    private final RecruteurRepository recruteurRepository;

    public RecruteurAdapter(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    @Override
    public int save(Recruteur recruteur) {
        final RecruteurEntity recruteurEntity = new RecruteurEntity(recruteur.getLanguage(), recruteur.getEmail(), recruteur.getExperienceEnAnnees());
        final RecruteurEntity saveRecruteurEntity = recruteurRepository.save(recruteurEntity);
        return saveRecruteurEntity.getId();
    }

    @Override
    public Optional<Recruteur> findById(int recruteurId) {
        final Optional<RecruteurEntity> optionalRecruteurEntity = recruteurRepository.findById(recruteurId);
        return optionalRecruteurEntity.map(recruteurEntity -> new Recruteur(recruteurEntity.getId(), recruteurEntity.getLanguage(), recruteurEntity.getEmail(), recruteurEntity.getExperienceInYears()));
    }
}
