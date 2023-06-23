package com.soat.planification_entretien.archi_hexa.domain.use_case;

import com.soat.planification_entretien.archi_hexa.domain.entity.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.entity.RecruteurExperimente;
import com.soat.planification_entretien.archi_hexa.domain.port.RecruteurPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListerRecuteurExperimente {

    private static final int EXPERIENCE_SEUIL = 10;
    private final RecruteurPort recruteurPort;

    public ListerRecuteurExperimente(RecruteurPort recruteurPort) {
        this.recruteurPort = recruteurPort;
    }

    public List<RecruteurExperimente> execute() {
        final List<Recruteur> recruteurs = recruteurPort.findByXp(EXPERIENCE_SEUIL);
        return recruteurs.stream()
                .map(RecruteurExperimente::new)
                .toList();
    }
}
