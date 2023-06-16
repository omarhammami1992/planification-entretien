package com.soat.planification_entretien.archi_hexa.infrastructure.db.adapter;

import com.soat.planification_entretien.archi_hexa.domain.entity.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.port.RecruteurPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.model.DbRecruteur;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.repository.RecruteurRepository;
import org.springframework.stereotype.Repository;
@Repository
public class DbRecruteurAdapter implements RecruteurPort {

    private final RecruteurRepository recruteurRepository;

    public DbRecruteurAdapter(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    @Override
    public Integer save(Recruteur recruteur) {
        DbRecruteur dbRecruteur = new DbRecruteur(recruteur.language(), recruteur.email(), recruteur.experienceEnAnnees());
        final DbRecruteur savedDbRecruteur = recruteurRepository.save(dbRecruteur);
        return savedDbRecruteur.getId();
    }
}
