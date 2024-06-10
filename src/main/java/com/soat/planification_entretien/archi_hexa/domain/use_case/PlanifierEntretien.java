package com.soat.planification_entretien.archi_hexa.domain.use_case;

import com.soat.planification_entretien.archi_hexa.domain.entity.Candidat;
import com.soat.planification_entretien.archi_hexa.domain.entity.Entretien;
import com.soat.planification_entretien.archi_hexa.domain.entity.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.exception.InvalideEntretienParams;
import com.soat.planification_entretien.archi_hexa.domain.port.CandidatPort;
import com.soat.planification_entretien.archi_hexa.domain.port.EmailPort;
import com.soat.planification_entretien.archi_hexa.domain.port.EntretienPort;
import com.soat.planification_entretien.archi_hexa.domain.port.RecruteurPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PlanifierEntretien {
    private final CandidatPort candidatPort;
    private final RecruteurPort recruteurPort;
    private final EntretienPort entretienPort;
    private final EmailPort emailPort;

    public PlanifierEntretien(CandidatPort candidatPort, RecruteurPort recruteurPort, EntretienPort entretienPort, EmailPort emailPort) {
        this.candidatPort = candidatPort;
        this.recruteurPort = recruteurPort;
        this.entretienPort = entretienPort;
        this.emailPort = emailPort;
    }

    public boolean execute(int candidatId, int recruteurId, LocalDateTime dateEtHeureDisponibiliteDuCandidat, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        Candidat candidat = candidatPort.findById(candidatId).get();
        Recruteur recruteur = recruteurPort.findById(recruteurId).get();

        try {
            Entretien entretien = new Entretien(candidat, recruteur, dateEtHeureDisponibiliteDuRecruteur, dateEtHeureDisponibiliteDuCandidat);
            entretienPort.save(entretien);
            emailPort.envoyerUnEmailDeConfirmationAuCandidat(candidat.getEmail(), dateEtHeureDisponibiliteDuCandidat);
            emailPort.envoyerUnEmailDeConfirmationAuRecruteur(recruteur.getEmail(), dateEtHeureDisponibiliteDuCandidat);
            return true;
        } catch (InvalideEntretienParams invalideEntretienParams) {
            return false;
        }
    }
}
