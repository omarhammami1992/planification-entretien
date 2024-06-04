package com.soat.candidat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soat.ATest;
import com.soat.planification_entretien.archi_hexa.application.controller.CandidatController;
import com.soat.planification_entretien.archi_hexa.application.dto.CandidatDto;
import com.soat.planification_entretien.archi_hexa.infrastructure.database.entity.CandidatEntity;
import com.soat.planification_entretien.archi_hexa.infrastructure.database.repository.CandidatRepository;
import io.cucumber.java.Before;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Quand;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class CreationCandidatATest extends ATest {

    @Autowired
    private CandidatRepository candidatRepository;

    private CandidatDto candidatDto;
    private Integer candidatId = 1;

    @Before
    @Override
    public void setUp() {
        initIntegrationTest();
    }

    @Override
    protected void initPath() {
        RestAssured.basePath = CandidatController.PATH;
    }

    @Etantdonné("un candidat {string} \\({string}) avec {int} ans d’expériences")
    public void unCandidatAvecAnsDExpériences(String language, String email, int experienceEnAnnees) {
        candidatDto = new CandidatDto(language, email, experienceEnAnnees);
    }

    @Quand("on tente d'enregistrer le candidat")
    public void onTenteDEnregistrerLeCandidat() throws JsonProcessingException {
        String body = objectMapper.writeValueAsString(candidatDto);
        initPath();
        response = given()
                .log().all()
                .header("Content-Type", ContentType.JSON)
                .body(body)
                .when()
                .post("/");
    }

    @Alors("le candidat est correctement enregistré avec ses informations {string}, {string} et {int} ans d’expériences")
    public void leCandidatEstCorrectementEnregistréAvecSesInformationsEtAnsDExpériences(String language, String email, int experienceEnAnnees) {
        response.then()
                .statusCode(HttpStatus.SC_CREATED);

        final CandidatEntity candidat = candidatRepository.findById(candidatId).get();
        assertThat(candidat).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(new CandidatEntity(language, email, experienceEnAnnees));
    }

    @Alors("l'enregistrement est refusé")
    public void lEnregistrementEstRefusé() {
        response.then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Et("le candidat n'est pas enregistré")
    public void leCandidatNEstPasEnregistré() {
        final Optional<CandidatEntity> candidat = candidatRepository.findById(candidatId);
        assertThat(candidat).isEmpty();
    }
}
