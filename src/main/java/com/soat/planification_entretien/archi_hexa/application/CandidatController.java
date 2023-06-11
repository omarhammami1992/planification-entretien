package com.soat.planification_entretien.archi_hexa.application;

import com.soat.planification_entretien.archi_hexa.domain.enity.Candidat;
import com.soat.planification_entretien.archi_hexa.domain.use_case.CreerCandidat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.created;

@RestController
@RequestMapping(CandidatController.PATH)
public class CandidatController {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    public static final String PATH = "/api/candidat";

    private final CreerCandidat creerCandidat;

    public CandidatController(CreerCandidat creerCandidat) {
        this.creerCandidat = creerCandidat;
    }

    @PostMapping("")
    public ResponseEntity<Integer> creer(@RequestBody CandidatRequest candidatRequest) {

        if (candidatRequest.language().isBlank() || !isEmail(candidatRequest.email()) || candidatRequest.experienceEnAnnees().isBlank() || Integer.parseInt(candidatRequest.experienceEnAnnees()) < 0) {
            return badRequest().build();
        }

        Candidat candidat = toCandidat(candidatRequest);
        final Integer id = creerCandidat.execute(candidat);

        return created(null).body(id);
    }

    private static Candidat toCandidat(CandidatRequest candidatRequest) {
        return new Candidat(null, candidatRequest.language(), candidatRequest.email(), Integer.parseInt(candidatRequest.experienceEnAnnees()));
    }

    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }
}
