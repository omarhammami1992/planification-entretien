package com.soat.planification_entretien.archi_hexa.domain.use_case;

import java.time.LocalDateTime;

import com.soat.planification_entretien.archi_hexa.domain.port.EmailPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.model.DbCandidat;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.model.DbEntretien;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.model.DbRecruteur;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.repository.CandidatRepository;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.repository.EntretienRepository;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.repository.RecruteurRepository;
import org.springframework.stereotype.Service;

@Service
public class PlanifierEntretien {
    private final CandidatRepository candidatRepository;
    private final RecruteurRepository recruteurRepository;
    private final EntretienRepository entretienRepository;
    private final EmailPort emailPort;

    public PlanifierEntretien(CandidatRepository candidatRepository, RecruteurRepository recruteurRepository, EntretienRepository entretienRepository, EmailPort emailPort) {
        this.candidatRepository = candidatRepository;
        this.recruteurRepository = recruteurRepository;
        this.entretienRepository = entretienRepository;
        this.emailPort = emailPort;
    }

    public boolean execute(int candidatId, int recruteurId, LocalDateTime dateEtHeureDisponibiliteDuCandidat, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        DbCandidat candidat = candidatRepository.findById(candidatId).get();
        DbRecruteur recruteur = recruteurRepository.findById(recruteurId).get();

        if (recruteur.getLanguage().equals(candidat.getLanguage())
                && recruteur.getExperienceInYears() > candidat.getExperienceInYears()
                && dateEtHeureDisponibiliteDuCandidat.equals(dateEtHeureDisponibiliteDuRecruteur)) {
            DbEntretien entretien = DbEntretien.of(candidat, recruteur, dateEtHeureDisponibiliteDuRecruteur);
            entretienRepository.save(entretien);
            emailPort.envoyerUnEmailDeConfirmationAuCandidat(candidat.getEmail(), dateEtHeureDisponibiliteDuCandidat);
            emailPort.envoyerUnEmailDeConfirmationAuRecruteur(recruteur.getEmail(), dateEtHeureDisponibiliteDuCandidat);
            return true;
        }
        return false;
    }

}
