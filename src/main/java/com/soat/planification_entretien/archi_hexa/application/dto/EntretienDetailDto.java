package com.soat.planification_entretien.archi_hexa.application.dto;

import java.time.LocalDateTime;

public record EntretienDetailDto(int id,
                                 String emailCandidat,
                                 String emailRecruteur,
                                 String language,
                                 LocalDateTime horaire) {
}
