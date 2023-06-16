package com.soat.planification_entretien.archi_hexa.application;

import com.soat.planification_entretien.archi_hexa.domain.entity.Candidat;
import com.soat.planification_entretien.archi_hexa.domain.use_case.CreerCandidat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.created;

@RestController
@RequestMapping(CandidatController.PATH)
public class CandidatController {

    public static final String PATH = "/api/candidat";

    private final CreerCandidat creerCandidat;

    public CandidatController(CreerCandidat creerCandidat) {
        this.creerCandidat = creerCandidat;
    }

    @PostMapping("")
    public ResponseEntity<Integer> creer(@RequestBody CandidatRequest candidatRequest) {
        if (candidatRequest.isValid()) {
            return badRequest().build();
        }
        Candidat candidat = toCandidat(candidatRequest);

        final Integer savedCandidatId = creerCandidat.execute(candidat);

        return created(null).body(savedCandidatId);
    }

    private static Candidat toCandidat(CandidatRequest candidatRequest) {
        return new Candidat(null, candidatRequest.language(), candidatRequest.email(), Integer.parseInt(candidatRequest.experienceEnAnnees()));
    }
}
