package com.soat.planification_entretien.archi_hexa.domain.use_case;

import com.soat.planification_entretien.archi_hexa.domain.EmailService;
import com.soat.planification_entretien.archi_hexa.infrastructure.model.JpaCandidat;
import com.soat.planification_entretien.archi_hexa.infrastructure.model.JpaEntretien;
import com.soat.planification_entretien.archi_hexa.infrastructure.model.JpaRecruteur;
import com.soat.planification_entretien.archi_hexa.infrastructure.repository.CandidatRepository;
import com.soat.planification_entretien.archi_hexa.infrastructure.repository.EntretienRepository;
import com.soat.planification_entretien.archi_hexa.infrastructure.repository.RecruteurRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PlanifierEntretien {
    private final CandidatRepository candidatRepository;
    private final RecruteurRepository recruteurRepository;
    private final EntretienRepository entretienRepository;
    private final EmailService emailService;

    public PlanifierEntretien(CandidatRepository candidatRepository, RecruteurRepository recruteurRepository, EntretienRepository entretienRepository, EmailService emailService) {
        this.candidatRepository = candidatRepository;
        this.recruteurRepository = recruteurRepository;
        this.entretienRepository = entretienRepository;
        this.emailService = emailService;
    }

    public boolean execute(int candidatId, int recruteurId, LocalDateTime dateEtHeureDisponibiliteDuCandidat, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        JpaCandidat candidat = candidatRepository.findById(candidatId).get();
        JpaRecruteur recruteur = recruteurRepository.findById(recruteurId).get();

        if (recruteur.getLanguage().equals(candidat.getLanguage())
                && recruteur.getExperienceInYears() > candidat.getExperienceInYears()
                && dateEtHeureDisponibiliteDuCandidat.equals(dateEtHeureDisponibiliteDuRecruteur)) {
            JpaEntretien entretien = JpaEntretien.of(candidat, recruteur, dateEtHeureDisponibiliteDuRecruteur);
            entretienRepository.save(entretien);
            emailService.envoyerUnEmailDeConfirmationAuCandidat(candidat.getEmail(), dateEtHeureDisponibiliteDuCandidat);
            emailService.envoyerUnEmailDeConfirmationAuRecruteur(recruteur.getEmail(), dateEtHeureDisponibiliteDuCandidat);
            return true;
        }
        return false;
    }

}
