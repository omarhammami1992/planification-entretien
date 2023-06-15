package com.soat.planification_entretien.archi_hexa.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.soat.planification_entretien.archi_hexa.application.EntretienDetailResponse;
import com.soat.planification_entretien.archi_hexa.infrastructure.model.Candidat;
import com.soat.planification_entretien.archi_hexa.infrastructure.model.Entretien;
import com.soat.planification_entretien.archi_hexa.infrastructure.model.Recruteur;
import com.soat.planification_entretien.archi_hexa.infrastructure.repository.CandidatRepository;
import com.soat.planification_entretien.archi_hexa.infrastructure.repository.EntretienRepository;
import com.soat.planification_entretien.archi_hexa.infrastructure.repository.RecruteurRepository;
import org.springframework.stereotype.Service;

@Service
public class EntretienService {
    private final CandidatRepository candidatRepository;
    private final RecruteurRepository recruteurRepository;
    private final EntretienRepository entretienRepository;
    private final EmailPort emailPort;

    public EntretienService(CandidatRepository candidatRepository, RecruteurRepository recruteurRepository, EntretienRepository entretienRepository, EmailPort emailPort) {
        this.candidatRepository = candidatRepository;
        this.recruteurRepository = recruteurRepository;
        this.entretienRepository = entretienRepository;
        this.emailPort = emailPort;
    }

    public boolean planifier(int candidatId, int recruteurId, LocalDateTime dateEtHeureDisponibiliteDuCandidat, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        Candidat candidat = candidatRepository.findById(candidatId).get();
        Recruteur recruteur = recruteurRepository.findById(recruteurId).get();

        if (recruteur.getLanguage().equals(candidat.getLanguage())
                && recruteur.getExperienceInYears() > candidat.getExperienceInYears()
                && dateEtHeureDisponibiliteDuCandidat.equals(dateEtHeureDisponibiliteDuRecruteur)) {
            Entretien entretien = Entretien.of(candidat, recruteur, dateEtHeureDisponibiliteDuRecruteur);
            entretienRepository.save(entretien);
            emailPort.envoyerUnEmailDeConfirmationAuCandidat(candidat.getEmail(), dateEtHeureDisponibiliteDuCandidat);
            emailPort.envoyerUnEmailDeConfirmationAuRecruteur(recruteur.getEmail(), dateEtHeureDisponibiliteDuCandidat);
            return true;
        }
        return false;
    }

    public List<EntretienDetailResponse> lister() {
        return entretienRepository.findAll().stream().map(entretien ->
                new EntretienDetailResponse(
                        entretien.getId(),
                        entretien.getCandidat().getEmail(),
                        entretien.getRecruteur().getEmail(),
                        entretien.getRecruteur().getLanguage(),
                        entretien.getHoraireEntretien())
        ).toList();
    }
}