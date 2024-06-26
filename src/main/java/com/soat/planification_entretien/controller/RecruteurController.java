package com.soat.planification_entretien.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.soat.planification_entretien.model.Recruteur;
import com.soat.planification_entretien.repository.RecruteurRepository;
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

    private final RecruteurRepository recruteurRepository;

    public RecruteurController(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    @PostMapping("")
    public ResponseEntity<Integer> creer(@RequestBody RecruteurDto recruteurDto) {
        if (recruteurDto.language().isBlank() || !isEmail(recruteurDto.email()) || recruteurDto.experienceEnAnnees() < 0) {
            return badRequest().build();
        }

        Recruteur recruteur = new Recruteur(recruteurDto.language(), recruteurDto.email(), recruteurDto.experienceEnAnnees());
        Recruteur savedRecruteur = recruteurRepository.save(recruteur);

        return created(null).body(savedRecruteur.getId());
    }

    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }
}
