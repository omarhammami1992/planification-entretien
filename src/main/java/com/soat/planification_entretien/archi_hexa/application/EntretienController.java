package com.soat.planification_entretien.archi_hexa.application;

import java.util.List;

import com.soat.planification_entretien.archi_hexa.domain.entity.EntretienDetail;
import com.soat.planification_entretien.archi_hexa.domain.use_case.ListerEntretien;
import com.soat.planification_entretien.archi_hexa.domain.use_case.PlanifierEntretien;
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

    private final PlanifierEntretien planifierEntretien;
    private final ListerEntretien listerEntretien;

    public EntretienController(PlanifierEntretien planifierEntretien, ListerEntretien listerEntretien) {
        this.planifierEntretien = planifierEntretien;
        this.listerEntretien = listerEntretien;
    }

    @GetMapping("/")
    public ResponseEntity<List<EntretienDetailResponse>> findAll() {
        final List<EntretienDetail> entretienDetails = listerEntretien.execute();
        final List<EntretienDetailResponse> entretienDetailResponses = entretienDetails.stream()
                .map(EntretienController::toEntretienDetailResponse
        ).toList();
        return new ResponseEntity<>(entretienDetailResponses, HttpStatus.OK);
    }

    private static EntretienDetailResponse toEntretienDetailResponse(EntretienDetail entretien) {
        return new EntretienDetailResponse(
                entretien.id(),
                entretien.candidatEmail(),
                entretien.recruteurEmail(),
                entretien.language(),
                entretien.horaireEntretien());
    }

    @PostMapping("planifier")
    public ResponseEntity<Void> planifier(@RequestBody EntretienRequest entretienRequest) {

        var planifie = planifierEntretien.execute(entretienRequest.candidatId(), entretienRequest.recruteurId(), entretienRequest.disponibiliteDuCandidat(), entretienRequest.disponibiliteDuRecruteur());

        if (planifie) {
            return created(null).build();
        } else {
            return badRequest().build();
        }

    }
}
