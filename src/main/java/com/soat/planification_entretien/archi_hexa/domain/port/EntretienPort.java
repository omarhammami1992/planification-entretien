package com.soat.planification_entretien.archi_hexa.domain.port;

import com.soat.planification_entretien.archi_hexa.domain.enity.Entretien;

public interface EntretienPort {

    void save(Entretien entretien);
}
