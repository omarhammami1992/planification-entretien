package com.soat.planification_entretien.archi_hexa.domain.recruteur.use_case;

import com.soat.planification_entretien.archi_hexa.domain.recruteur.entity.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.recruteur.port.RecruteurPort;
import org.springframework.stereotype.Service;

@Service
public class CreerRecruteur {

    private final RecruteurPort recruteurPort;

    public CreerRecruteur(RecruteurPort recruteurPort) {
        this.recruteurPort = recruteurPort;
    }

    public int execute(String language, String email, int experienceEnAnnees) {
        final Recruteur recruteur = new Recruteur(language, email, experienceEnAnnees);
        return recruteurPort.save(recruteur);
    }

}
