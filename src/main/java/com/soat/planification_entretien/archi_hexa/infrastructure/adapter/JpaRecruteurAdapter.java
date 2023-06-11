package com.soat.planification_entretien.archi_hexa.infrastructure.adapter;

import com.soat.planification_entretien.archi_hexa.domain.enity.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.port.RecruteurPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.model.JpaRecruteur;
import com.soat.planification_entretien.archi_hexa.infrastructure.repository.RecruteurRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class JpaRecruteurAdapter implements RecruteurPort {
    private final RecruteurRepository recruteurRepository;

    public JpaRecruteurAdapter(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    @Override
    public Integer save(Recruteur recruteur) {
        JpaRecruteur jpaRecruteur = toJpaRecruteur(recruteur);
        JpaRecruteur savedRecruteur = recruteurRepository.save(jpaRecruteur);
        return savedRecruteur.getId();
    }

    private static JpaRecruteur toJpaRecruteur(Recruteur recruteur) {
        return new JpaRecruteur(recruteur.language(), recruteur.email(), recruteur.experienceEnAnnees());
    }

    @Override
    public Optional<Recruteur> findById(Integer id) {
        final Optional<JpaRecruteur> optionalJpaRecruteur = recruteurRepository.findById(id);
        return optionalJpaRecruteur.map(JpaRecruteurAdapter::toRecruteur);
    }

    private static Recruteur toRecruteur(JpaRecruteur jpaRecruteur) {
        return new Recruteur(
                jpaRecruteur.getId(),
                jpaRecruteur.getLanguage(),
                jpaRecruteur.getEmail(),
                jpaRecruteur.getExperienceInYears()
        );
    }
}
