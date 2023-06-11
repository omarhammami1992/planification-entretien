package com.soat.planification_entretien.archi_hexa.infrastructure.adapter;

import com.soat.planification_entretien.archi_hexa.domain.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.port.RecruteurPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.model.JpaRecruteur;
import com.soat.planification_entretien.archi_hexa.infrastructure.repository.RecruteurRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JpaRecruteurAdapter implements RecruteurPort {
    private final RecruteurRepository recruteurRepository;

    public JpaRecruteurAdapter(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    @Override
    public Integer save(Recruteur recruteur) {
        JpaRecruteur jpaRecruteur = new JpaRecruteur(recruteur.language(), recruteur.email(), recruteur.experienceEnAnnees());
        JpaRecruteur savedRecruteur = recruteurRepository.save(jpaRecruteur);
        return savedRecruteur.getId();
    }
}
