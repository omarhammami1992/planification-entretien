package com.soat.planification_entretien.archi_hexa.domain;

import com.soat.planification_entretien.archi_hexa.application.EntretienDetailDto;
import com.soat.planification_entretien.archi_hexa.infrastructure.repository.EntretienRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ListerEntretien {

    private final EntretienRepository entretienRepository;

    public ListerEntretien(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
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


