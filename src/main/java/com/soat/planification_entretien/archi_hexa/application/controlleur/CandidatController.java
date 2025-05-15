package com.soat.planification_entretien.archi_hexa.application.controlleur;

import com.soat.planification_entretien.archi_hexa.application.dto.CandidatDto;
import com.soat.planification_entretien.archi_hexa.domain.exception.InvalidParams;
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
    public ResponseEntity<Integer> creer(@RequestBody CandidatDto candidatDto) {

        try {
            Integer savedId = creerCandidat.execute(candidatDto.language(), candidatDto.email(), candidatDto.experienceEnAnnees());
            return created(null).body(savedId);

        } catch (InvalidParams e) {
            return badRequest().build();
        }
    }

}
