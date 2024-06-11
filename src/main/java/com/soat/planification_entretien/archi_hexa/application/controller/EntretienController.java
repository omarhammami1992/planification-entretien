package com.soat.planification_entretien.archi_hexa.application.controller;

import java.util.List;

import com.soat.planification_entretien.archi_hexa.application.dto.EntretienDetailDto;
import com.soat.planification_entretien.archi_hexa.application.dto.EntretienDto;
import com.soat.planification_entretien.archi_hexa.domain.entity.Entretien;
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
    public static final String PATH = "/api/entretien";

    private final PlanifierEntretien planifierEntretien;
    private final ListerEntretien listerEntretien;

    public EntretienController(PlanifierEntretien planifierEntretien, ListerEntretien listerEntretien) {
        this.planifierEntretien = planifierEntretien;
        this.listerEntretien = listerEntretien;
    }

    @GetMapping
    public ResponseEntity<List<EntretienDetailDto>> findAll() {
        final List<Entretien> entretiens = listerEntretien.execute();
        final List<EntretienDetailDto> entretienDetailDtos = entretiens.stream()
                .map(EntretienController::toEntretienDetailDto).toList();
        return new ResponseEntity<>(entretienDetailDtos, HttpStatus.OK);
    }

    @PostMapping("/planifier")
    public ResponseEntity<Void> planifier(@RequestBody EntretienDto entretienDto) {

        var planifie = planifierEntretien.execute(entretienDto.candidatId(), entretienDto.recruteurId(), entretienDto.disponibiliteDuCandidat(), entretienDto.disponibiliteDuRecruteur());

        if (planifie) {
            return created(null).build();
        } else {
            return badRequest().build();
        }

    }

    private static EntretienDetailDto toEntretienDetailDto(Entretien entretien) {
        return new EntretienDetailDto(
                entretien.getId(),
                entretien.getCandidat().getEmail(),
                entretien.getRecruteur().getEmail(),
                entretien.getRecruteur().getLanguage(),
                entretien.getHoraireEntretien());
    }
}
