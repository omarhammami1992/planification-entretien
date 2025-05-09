package com.soat.planification_entretien.controller;

import com.soat.planification_entretien.model.Candidat;
import com.soat.planification_entretien.repository.CandidatRepository;
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

    private final CandidatRepository candidatRepository;

    public CandidatController(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    @PostMapping("")
    public ResponseEntity<Integer> creer(@RequestBody CandidatDto candidatDto) {

        if (candidatDto.language().isBlank() || !isEmail(candidatDto.email()) || candidatDto.experienceEnAnnees() < 0) {
            return badRequest().build();
        }

        Candidat candidat = new Candidat(candidatDto.language(), candidatDto.email(), candidatDto.experienceEnAnnees());
        Candidat savedCandidat = candidatRepository.save(candidat);

        return created(null).body(savedCandidat.getId());
    }

    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }
}
