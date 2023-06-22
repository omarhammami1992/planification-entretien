package com.soat.planification_entretien.archi_hexa.domain.entity;

import java.time.LocalDateTime;

public record EntretienDetail(
        Integer id,

        String candidatEmail,
        String recruteurEmail,
        String language,
        LocalDateTime horaireEntretien
        ) {
}
