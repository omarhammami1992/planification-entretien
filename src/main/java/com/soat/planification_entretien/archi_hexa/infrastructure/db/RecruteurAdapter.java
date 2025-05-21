package com.soat.planification_entretien.archi_hexa.infrastructure.db;

import com.soat.planification_entretien.archi_hexa.domain.entity.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.port.RecruteurPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.entity.DBRecruteur;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.repository.RecruteurRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RecruteurAdapter implements RecruteurPort {

    private final RecruteurRepository recruteurRepository;

    public RecruteurAdapter(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    @Override
    public Integer save(Recruteur recruteur) {
        DBRecruteur Recruteur = new DBRecruteur(
                recruteur.getLanguage(),
                recruteur.getEmail(),
                recruteur.getExperienceEnAnnees()
        );
        DBRecruteur savedRecruteur = recruteurRepository.save(Recruteur);
        return savedRecruteur.getId();
    }
}
