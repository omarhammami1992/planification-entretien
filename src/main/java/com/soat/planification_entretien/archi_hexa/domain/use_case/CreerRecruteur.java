package com.soat.planification_entretien.archi_hexa.domain.use_case;

import com.soat.planification_entretien.archi_hexa.domain.entity.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.port.RecruteurPort;
import org.springframework.stereotype.Service;

@Service
public class CreerRecruteur {

    private final RecruteurPort recruteurPort;

    public CreerRecruteur(RecruteurPort recruteurPort) {
        this.recruteurPort = recruteurPort;
    }

    public Integer execute(Recruteur recruteur) {
        return recruteurPort.save(recruteur);
    }
}
