package com.soat.planification_entretien.archi_hexa.domain.entretien;

import java.time.LocalDateTime;
import java.util.List;

import com.soat.planification_entretien.archi_hexa.application.dto.EntretienDetailDto;
import com.soat.planification_entretien.archi_hexa.infrastructure.database.entity.CandidatEntity;
import com.soat.planification_entretien.archi_hexa.infrastructure.database.entity.EntretienEntity;
import com.soat.planification_entretien.archi_hexa.infrastructure.database.entity.RecruteurEntity;
import com.soat.planification_entretien.archi_hexa.infrastructure.database.repository.CandidatRepository;
import com.soat.planification_entretien.archi_hexa.infrastructure.database.repository.EntretienRepository;
import com.soat.planification_entretien.archi_hexa.infrastructure.database.repository.RecruteurRepository;
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
        CandidatEntity candidat = candidatRepository.findById(candidatId).get();
        RecruteurEntity recruteur = recruteurRepository.findById(recruteurId).get();

        if (recruteur.getLanguage().equals(candidat.getLanguage())
                && recruteur.getExperienceInYears() > candidat.getExperienceInYears()
                && dateEtHeureDisponibiliteDuCandidat.equals(dateEtHeureDisponibiliteDuRecruteur)) {
            EntretienEntity entretien = EntretienEntity.of(candidat, recruteur, dateEtHeureDisponibiliteDuRecruteur);
            entretienRepository.save(entretien);
            emailPort.envoyerUnEmailDeConfirmationAuCandidat(candidat.getEmail(), dateEtHeureDisponibiliteDuCandidat);
            emailPort.envoyerUnEmailDeConfirmationAuRecruteur(recruteur.getEmail(), dateEtHeureDisponibiliteDuCandidat);
            return true;
        }
        return false;
    }

    public List<EntretienDetailDto> lister() {
        return entretienRepository.findAll().stream().map(entretien ->
                new EntretienDetailDto(
                        entretien.getId(),
                        entretien.getCandidat().getEmail(),
                        entretien.getRecruteur().getEmail(),
                        entretien.getRecruteur().getLanguage(),
                        entretien.getHoraireEntretien())
        ).toList();
    }
}