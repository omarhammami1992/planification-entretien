package com.soat.planification_entretien.archi_hexa.application.controller;

import com.soat.planification_entretien.archi_hexa.application.dto.RecruteurDto;
import com.soat.planification_entretien.archi_hexa.domain.recruteur.use_case.CreerRecruteur;
import com.soat.planification_entretien.archi_hexa.domain.recruteur.exception.InvalideRecruteurParams;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.created;

@RestController
@RequestMapping(RecruteurController.PATH)
public class RecruteurController {
    public static final String PATH = "/api/recruteur";

    private final CreerRecruteur creerRecruteur;

    public RecruteurController(CreerRecruteur creerRecruteur) {
        this.creerRecruteur = creerRecruteur;
    }

    @PostMapping("")
    public ResponseEntity<Integer> creer(@RequestBody RecruteurDto recruteurDto) {
        try {
            int savedRecruteurId = creerRecruteur.execute(recruteurDto.language(), recruteurDto.email(), recruteurDto.experienceEnAnnees());
            return created(null).body(savedRecruteurId);
        } catch (InvalideRecruteurParams e) {
            return badRequest().build();
        }
    }

}
