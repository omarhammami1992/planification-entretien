package com.soat.planification_entretien.archi_hexa.domain.use_case;

import com.soat.planification_entretien.archi_hexa.domain.enity.EntretienDetail;
import com.soat.planification_entretien.archi_hexa.domain.port.EntretienPort;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ListerEntretien {

    private final EntretienPort entretienPort;

    public ListerEntretien(EntretienPort entretienPort) {
        this.entretienPort = entretienPort;
    }

    public List<EntretienDetail> execute() {
        return entretienPort.findAll();
    }
}


