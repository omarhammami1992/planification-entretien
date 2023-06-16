package com.soat.planification_entretien.archi_hexa.domain.use_case;

import com.soat.planification_entretien.archi_hexa.application.EntretienDetailResponse;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.repository.EntretienRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListerEntretien {
    private final EntretienRepository entretienRepository;

    public ListerEntretien(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }


    public List<EntretienDetailResponse> execute() {
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
