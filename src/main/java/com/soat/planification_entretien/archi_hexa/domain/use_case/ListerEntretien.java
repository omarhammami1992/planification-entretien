package com.soat.planification_entretien.archi_hexa.domain.use_case;

import com.soat.planification_entretien.archi_hexa.application.dto.EntretienDetailDto;
import com.soat.planification_entretien.archi_hexa.domain.port.EmailPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.database.repository.CandidatRepository;
import com.soat.planification_entretien.archi_hexa.infrastructure.database.repository.EntretienRepository;
import com.soat.planification_entretien.archi_hexa.infrastructure.database.repository.RecruteurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListerEntretien {
    private final CandidatRepository candidatRepository;
    private final RecruteurRepository recruteurRepository;
    private final EntretienRepository entretienRepository;
    private final EmailPort emailPort;

    public ListerEntretien(CandidatRepository candidatRepository, RecruteurRepository recruteurRepository, EntretienRepository entretienRepository, EmailPort emailPort) {
        this.candidatRepository = candidatRepository;
        this.recruteurRepository = recruteurRepository;
        this.entretienRepository = entretienRepository;
        this.emailPort = emailPort;
    }

    public List<EntretienDetailDto> execute() {
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
