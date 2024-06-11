package com.soat.planification_entretien.archi_hexa.domain.port;

import java.time.LocalDateTime;

public interface EmailPort {
    void envoyerUnEmailDeConfirmationAuCandidat(String email, LocalDateTime horaire);

    void envoyerUnEmailDeConfirmationAuRecruteur(String email, LocalDateTime horaire);
}