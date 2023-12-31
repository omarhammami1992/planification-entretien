package com.soat.planification_entretien.controller;

import java.time.LocalDateTime;

public record EntretienDto(int candidatId, int recruteurId, LocalDateTime disponibiliteDuCandidat,
                           LocalDateTime disponibiliteDuRecruteur) {
}
