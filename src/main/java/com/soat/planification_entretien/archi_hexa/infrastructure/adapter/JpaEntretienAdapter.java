package com.soat.planification_entretien.archi_hexa.infrastructure.adapter;

import com.soat.planification_entretien.archi_hexa.application.EntretienDetailDto;
import com.soat.planification_entretien.archi_hexa.domain.enity.Candidat;
import com.soat.planification_entretien.archi_hexa.domain.enity.Entretien;
import com.soat.planification_entretien.archi_hexa.domain.enity.EntretienDetail;
import com.soat.planification_entretien.archi_hexa.domain.enity.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.port.EntretienPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.model.JpaCandidat;
import com.soat.planification_entretien.archi_hexa.infrastructure.model.JpaEntretien;
import com.soat.planification_entretien.archi_hexa.infrastructure.model.JpaRecruteur;
import com.soat.planification_entretien.archi_hexa.infrastructure.repository.EntretienRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaEntretienAdapter  implements EntretienPort {
    private final EntretienRepository entretienRepository;

    public JpaEntretienAdapter(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    @Override
    public void save(Entretien entretien) {
        final JpaCandidat jpaCandidat = toJpaCandidat(entretien.candidat());
        final JpaRecruteur jpaRecruteur = toJpaRecruteur(entretien.recruteur());

        JpaEntretien jpaEntretien = JpaEntretien.of(jpaCandidat, jpaRecruteur, entretien.horaireEntretien());
        entretienRepository.save(jpaEntretien);
    }

    @Override
    public List<EntretienDetail> findAll() {
        final List<JpaEntretien> jpaEntretiens = entretienRepository.findAll();
        return jpaEntretiens.stream()
                .map(JpaEntretienAdapter::toEntretienDetail)
                .toList();
    }

    private static EntretienDetail toEntretienDetail(JpaEntretien jpaEntretien) {
        return new EntretienDetail(
                jpaEntretien.getId(),
                jpaEntretien.getCandidat().getEmail(),
                jpaEntretien.getRecruteur().getEmail(),
                jpaEntretien.getRecruteur().getLanguage(),
                jpaEntretien.getHoraireEntretien());
    }

    private static JpaCandidat toJpaCandidat(Candidat candidat) {
        return new JpaCandidat(candidat.id(), candidat.language(), candidat.email(), candidat.experienceEnAnnees());
    }
    private static JpaRecruteur toJpaRecruteur(Recruteur recruteur) {
        return new JpaRecruteur(recruteur.id(), recruteur.language(), recruteur.email(), recruteur.experienceEnAnnees());
    }
}
