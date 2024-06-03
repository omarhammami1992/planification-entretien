package com.soat.recruteur;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.soat.ATest;
import com.soat.planification_entretien.archi_hexa.application.controller.RecruteurController;
import com.soat.planification_entretien.archi_hexa.application.dto.RecruteurDto;
import com.soat.planification_entretien.archi_hexa.infrastructure.database.entity.Recruteur;
import com.soat.planification_entretien.archi_hexa.infrastructure.database.repository.RecruteurRepository;
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

public class CreationRecruteurATest extends ATest {

    @Autowired
    private RecruteurRepository recruteurRepository;

    private RecruteurDto recruteurDto;
    private Integer recruteurId = 1;

    @Before
    @Override
    public void setUp() {
        initIntegrationTest();
    }

    @Override
    protected void initPath() {
        RestAssured.basePath = RecruteurController.PATH;
    }

    @Etantdonné("un recruteur {string} \\({string}) avec {int} ans d’expériences")
    public void unRecruteurAvecAnsDExpériences(String language, String email, int experienceEnAnnees) {
        recruteurDto = new RecruteurDto(language, email, experienceEnAnnees);
    }

    @Quand("on tente d'enregistrer le recruteur")
    public void onTenteDEnregistrerLeRecruteur() throws JsonProcessingException {
        String body = objectMapper.writeValueAsString(recruteurDto);
        initPath();
        response = given()
                .log().all()
                .header("Content-Type", ContentType.JSON)
                .body(body)
        .when()
                .post("/");
    }

    @Alors("le recruteur est correctement enregistré avec ses informations {string}, {string} et {int} ans d’expériences")
    public void leRecruteurEstCorrectementEnregistréAvecSesInformationsEtAnsDExpériences(String language, String email, int experienceEnAnnees) {
        response.then()
                .statusCode(HttpStatus.SC_CREATED);

        final Recruteur recruteur = recruteurRepository.findById(recruteurId).get();
        assertThat(recruteur).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(new Recruteur(language, email, experienceEnAnnees));
    }

    @Alors("l'enregistrement du recruteur est refusé")
    public void lEnregistrementDuRecruteurEstRefusé() {
        response.then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Et("le recruteur n'est pas enregistré")
    public void leRecruteurNEstPasEnregistré() {
        final Optional<Recruteur> recruteur = recruteurRepository.findById(recruteurId);
        assertThat(recruteur).isEmpty();
    }
}
