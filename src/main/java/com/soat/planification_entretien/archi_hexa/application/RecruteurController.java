package com.soat.planification_entretien.archi_hexa.application;

import com.soat.planification_entretien.archi_hexa.domain.entity.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.use_case.CreerRecruteur;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(RecruteurController.PATH)
public class RecruteurController {

    public static final String PATH = "/api/recruteur";
    private final CreerRecruteur creerRecruteur;

    public RecruteurController(CreerRecruteur creerRecruteur) {
        this.creerRecruteur = creerRecruteur;
    }

    @PostMapping("")
    public ResponseEntity<Integer> creer(@RequestBody RecruteurRequest recruteurRequest) {
        if (recruteurRequest.isValid()) {
            return badRequest().build();
        }

        Recruteur recruteur = new Recruteur(null, recruteurRequest.language(), recruteurRequest.email(), Integer.parseInt(recruteurRequest.experienceEnAnnees()));
        final Integer saveRecruteurId = creerRecruteur.execute(recruteur);

        return created(null).body(saveRecruteurId);
    }
}
