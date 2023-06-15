package com.soat.planification_entretien.archi_hexa.application;

import java.time.LocalDateTime;

public record EntretienDetailResponse(int id, String emailCandidat, String emailRecruteur, String language,
                                      LocalDateTime horaire) {
}
