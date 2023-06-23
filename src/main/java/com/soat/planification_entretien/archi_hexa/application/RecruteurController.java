package com.soat.planification_entretien.archi_hexa.application;

import com.soat.planification_entretien.archi_hexa.domain.entity.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.entity.RecruteurExperimente;
import com.soat.planification_entretien.archi_hexa.domain.use_case.CreerRecruteur;
import com.soat.planification_entretien.archi_hexa.domain.use_case.ListerRecuteurExperimente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(RecruteurController.PATH)
public class RecruteurController {

    public static final String PATH = "/api/recruteur";
    private final CreerRecruteur creerRecruteur;
    private final ListerRecuteurExperimente listerRecuteurExperimente;

    public RecruteurController(CreerRecruteur creerRecruteur, ListerRecuteurExperimente listerRecuteurExperimente) {
        this.creerRecruteur = creerRecruteur;
        this.listerRecuteurExperimente = listerRecuteurExperimente;
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
    @GetMapping("experimente")
    public ResponseEntity<List<RecruteurExperimenteResponse>> experimente() {
        final List<RecruteurExperimente> recruteurExperimentes = listerRecuteurExperimente.execute();
        final List<RecruteurExperimenteResponse> recruteurExperimenteResponses = recruteurExperimentes.stream().map(RecruteurController::toRecruteurExperimenteResponse).toList();
        return new ResponseEntity<>(recruteurExperimenteResponses, HttpStatus.OK);
    }

    private static RecruteurExperimenteResponse toRecruteurExperimenteResponse(RecruteurExperimente recruteurExperimente) {
        return new RecruteurExperimenteResponse(
                recruteurExperimente.getId(), recruteurExperimente.getCompetence(), recruteurExperimente.getEmail()
        );
    }
}
