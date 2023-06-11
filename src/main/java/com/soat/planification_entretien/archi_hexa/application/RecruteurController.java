package com.soat.planification_entretien.archi_hexa.application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.soat.planification_entretien.archi_hexa.domain.use_case.CreerRecruteur;
import com.soat.planification_entretien.archi_hexa.domain.enity.Recruteur;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(RecruteurController.PATH)
public class RecruteurController {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
    public static final String PATH = "/api/recruteur";

    private final CreerRecruteur creerRecruteur;

    public RecruteurController(CreerRecruteur creerRecruteur) {
        this.creerRecruteur = creerRecruteur;
    }

    @PostMapping("")
    public ResponseEntity<Integer> creer(@RequestBody RecruteurRequest recruteurRequest) {
        if (recruteurRequest.language().isBlank() || !isEmail(recruteurRequest.email()) || recruteurRequest.experienceEnAnnees().isBlank() || Integer.parseInt(recruteurRequest.experienceEnAnnees()) < 0) {
            return badRequest().build();
        }
        final Recruteur recruteur = toRecruteur(recruteurRequest);
        final Integer savedId = creerRecruteur.execute(recruteur);
        return created(null).body(savedId);
    }

    private static Recruteur toRecruteur(RecruteurRequest recruteurRequest) {
        return new Recruteur(null, recruteurRequest.language(), recruteurRequest.email(), Integer.parseInt(recruteurRequest.experienceEnAnnees()));
    }

    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }
}
