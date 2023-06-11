package com.soat.planification_entretien.archi_hexa.domain.enity;

import java.time.LocalDateTime;

public record Entretien(Candidat candidat,
                        Recruteur recruteur,
                        LocalDateTime horaireEntretien) {
}
