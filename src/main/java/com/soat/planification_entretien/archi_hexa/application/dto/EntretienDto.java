package com.soat.planification_entretien.archi_hexa.application.dto;

import java.time.LocalDateTime;

public record EntretienDto(int candidatId, int recruteurId, LocalDateTime disponibiliteDuCandidat,
                           LocalDateTime disponibiliteDuRecruteur) {
}
