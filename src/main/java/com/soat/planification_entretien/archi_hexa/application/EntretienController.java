package com.soat.planification_entretien.archi_hexa.application;

import java.util.List;

import com.soat.planification_entretien.archi_hexa.domain.EntretienService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(EntretienController.PATH)
public class EntretienController {
    public static final String PATH = "/api/entretien/";

    private final EntretienService entretienService;

    public EntretienController(EntretienService entretienService) {
        this.entretienService = entretienService;
    }

    @GetMapping("/")
    public ResponseEntity<List<EntretienDetailResponse>> findAll() {
        return new ResponseEntity<>(entretienService.lister(), HttpStatus.OK);
    }

    @PostMapping("planifier")
    public ResponseEntity<Void> planifier(@RequestBody EntretienRequest entretienRequest) {

        var planifie = entretienService.planifier(entretienRequest.candidatId(), entretienRequest.recruteurId(), entretienRequest.disponibiliteDuCandidat(), entretienRequest.disponibiliteDuRecruteur());

        if (planifie) {
            return created(null).build();
        } else {
            return badRequest().build();
        }

    }
}
