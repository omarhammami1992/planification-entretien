package com.soat.planification_entretien.archi_hexa.domain.use_case;

import java.time.LocalDateTime;
import java.util.List;

import com.soat.planification_entretien.archi_hexa.application.dto.EntretienDetailDto;
import com.soat.planification_entretien.archi_hexa.domain.port.EmailPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.entity.DBCandidat;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.entity.DBEntretien;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.entity.DBRecruteur;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.repository.CandidatRepository;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.repository.EntretienRepository;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.repository.RecruteurRepository;
import org.springframework.stereotype.Service;

@Service
public class EntretienService {
    private final CandidatRepository candidatRepository;
    private final RecruteurRepository recruteurRepository;
    private final EntretienRepository entretienRepository;
    private final EmailPort emailService;

    public EntretienService(CandidatRepository candidatRepository, RecruteurRepository recruteurRepository, EntretienRepository entretienRepository, EmailPort emailService) {
        this.candidatRepository = candidatRepository;
        this.recruteurRepository = recruteurRepository;
        this.entretienRepository = entretienRepository;
        this.emailService = emailService;
    }

    public boolean planifier(int candidatId, int recruteurId, LocalDateTime dateEtHeureDisponibiliteDuCandidat, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        DBCandidat candidat = candidatRepository.findById(candidatId).get();
        DBRecruteur recruteur = recruteurRepository.findById(recruteurId).get();

        if (recruteur.getLanguage().equals(candidat.getLanguage())
                && recruteur.getExperienceInYears() > candidat.getExperienceInYears()
                && dateEtHeureDisponibiliteDuCandidat.equals(dateEtHeureDisponibiliteDuRecruteur)) {
            DBEntretien entretien = DBEntretien.of(candidat, recruteur, dateEtHeureDisponibiliteDuRecruteur);
            entretienRepository.save(entretien);
            emailService.envoyerUnEmailDeConfirmationAuCandidat(candidat.getEmail(), dateEtHeureDisponibiliteDuCandidat);
            emailService.envoyerUnEmailDeConfirmationAuRecruteur(recruteur.getEmail(), dateEtHeureDisponibiliteDuCandidat);
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
