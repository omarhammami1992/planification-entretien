package com.soat.planification_entretien;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soat.ATest;
import com.soat.planification_entretien.archi_hexa.application.controlleur.EntretienController;
import com.soat.planification_entretien.archi_hexa.application.dto.EntretienDto;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.entity.DBCandidat;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.entity.DBEntretien;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.entity.DBRecruteur;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.repository.CandidatRepository;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.repository.EntretienRepository;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.repository.RecruteurRepository;
import com.soat.planification_entretien.archi_hexa.domain.port.EmailPort;
import io.cucumber.java.Before;
import io.cucumber.java.fr.*;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestEntityManager
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext
@CucumberContextConfiguration
@ActiveProfiles("AcceptanceTest")
public class PlafinicationEntretienATest extends ATest {

    private DBCandidat candidat;
    private LocalDateTime disponibiliteDuCandidat;
    private DBRecruteur recruteur;
    private LocalDateTime disponibiliteDuRecruteur;

    @Autowired
    private EntretienRepository entretienRepository;

    @Autowired
    private RecruteurRepository recruteurRepository;

    @Autowired
    private CandidatRepository candidatRepository;

    @Autowired
    private EmailPort emailService;

    @Before
    @Override
    public void setUp() {
        initIntegrationTest();
    }

    @Override
    protected void initPath() {
        RestAssured.basePath = EntretienController.PATH;
    }

    @Etantdonné("un candidat {string} \\({string}) avec {string} ans d’expériences qui est disponible {string} à {string}")
    public void unCandidatAvecAnsDExpériencesQuiEstDisponibleÀ(String language, String email, String experienceInYears, String date, String time) {
        candidat = new DBCandidat(language, email, Integer.parseInt(experienceInYears));
        candidatRepository.save(candidat);
        disponibiliteDuCandidat = LocalDateTime.of(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")));
    }

    @Etqu("un recruteur {string} \\({string}) qui a {string} ans d’XP qui est dispo {string} à {string}")
    public void unRecruteurQuiAAnsDXPQuiEstDispo(String language, String email, String experienceInYears, String date, String time) {
        recruteur = new DBRecruteur(language, email, Integer.parseInt(experienceInYears));
        recruteurRepository.save(recruteur);
        disponibiliteDuRecruteur = LocalDateTime.of(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")));
    }

    @Quand("on tente une planification d’entretien")
    public void onTenteUnePlanificationDEntretien() throws JsonProcessingException {
        EntretienDto entretienDto = new EntretienDto(candidat.getId(), recruteur.getId(), disponibiliteDuCandidat, disponibiliteDuRecruteur);
        String body = objectMapper.writeValueAsString(entretienDto);
        initPath();
        response = given()
                .log().all()
                .header("Content-Type", ContentType.JSON)
                .body(body)
                .when()
                .post("planifier");
    }

    @Alors("L’entretien est planifié")
    public void lEntretienEstPlanifié() {
        response.then()
                .statusCode(HttpStatus.SC_CREATED);

        DBEntretien entretien = entretienRepository.findByCandidat(candidat);
        DBEntretien expectedEntretien = DBEntretien.of(candidat, recruteur, disponibiliteDuCandidat);
        assertThat(entretien).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedEntretien);
    }

    @Et("un mail de confirmation est envoyé au candidat et au recruteur")
    public void unMailDeConfirmationEstEnvoyéAuCandidatEtAuRecruteur() {
        verify(emailService).envoyerUnEmailDeConfirmationAuCandidat(candidat.getEmail(), disponibiliteDuCandidat);
        verify(emailService).envoyerUnEmailDeConfirmationAuRecruteur(recruteur.getEmail(), disponibiliteDuCandidat);
    }

    @Alors("L’entretien n'est pas planifié")
    public void lEntretienNEstPasPlanifié() {
        response.then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);

        DBEntretien entretien = entretienRepository.findByCandidat(candidat);
        assertThat(entretien).isNull();
    }

    @Et("aucun mail de confirmation n'est envoyé au candidat ou au recruteur")
    public void aucunMailDeConfirmationNEstEnvoyéAuCandidatOuAuRecruteur() {
        verify(emailService, never()).envoyerUnEmailDeConfirmationAuCandidat(candidat.getEmail(), disponibiliteDuCandidat);
        verify(emailService, never()).envoyerUnEmailDeConfirmationAuRecruteur(recruteur.getEmail(), disponibiliteDuCandidat);
    }
}
