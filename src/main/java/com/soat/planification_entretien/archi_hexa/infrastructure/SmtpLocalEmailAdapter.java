package com.soat.planification_entretien.archi_hexa.infrastructure;

import java.time.LocalDateTime;

import com.soat.planification_entretien.archi_hexa.domain.EmailPort;
import org.springframework.stereotype.Service;

@Service
public class SmtpLocalEmailAdapter implements EmailPort {
    @Override
    public void envoyerUnEmailDeConfirmationAuCandidat(String email, LocalDateTime horaire) {

    }

    @Override
    public void envoyerUnEmailDeConfirmationAuRecruteur(String email, LocalDateTime horaire) {

    }
}
