package com.soat.planification_entretien;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.soat.ATest;
import com.soat.planification_entretien.archi_hexa.application.controlleur.EntretienController;
import com.soat.planification_entretien.archi_hexa.application.dto.EntretienDetailDto;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.entity.DBCandidat;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.entity.DBEntretien;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.entity.DBRecruteur;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Quand;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.transaction.annotation.Transactional;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

@Transactional
public class ListingEntretienATest extends ATest {

    private List<DBCandidat> savedCandidats = new ArrayList<>();
    private List<DBRecruteur> savedRecruteurs = new ArrayList<>();

    @Before
    @Override
    public void setUp() {
        initIntegrationTest();
    }

    @Override
    protected void initPath() {
        RestAssured.basePath = EntretienController.PATH;
    }

    @Etantdonné("les recruteurs existants")
    public void lesRecruteursExistants(DataTable dataTable) {
        List<DBRecruteur> recruteurs = dataTableTransformEntries(dataTable, this::buildRecruteur);

        for (DBRecruteur recruteur : recruteurs) {
            DBRecruteur saved = entityManager.persist(recruteur);
            savedRecruteurs.add(saved);
        }
    }

    private DBRecruteur buildRecruteur(Map<String, String> entry) {
        return new DBRecruteur(
                entry.get("language"),
                entry.get("email"),
                Integer.parseInt(entry.get("xp")));
    }

    @Et("les candidats existants")
    public void lesCandidatsExistants(DataTable dataTable) {
        List<DBCandidat> candidats = dataTableTransformEntries(dataTable, this::buildCandidat);

        for (DBCandidat candidat : candidats) {
            DBCandidat saved = entityManager.persist(candidat);
            savedCandidats.add(saved);
        }
    }

    private DBCandidat buildCandidat(Map<String, String> entry) {
        return new DBCandidat(
                entry.get("language"),
                entry.get("email"),
                Integer.parseInt(entry.get("xp")));
    }

    @Et("les entretiens existants")
    public void lesEntretiensExistants(DataTable dataTable) {
        List<DBEntretien> entretiens = dataTableTransformEntries(dataTable, this::buildEntretien);

        for (DBEntretien entretien : entretiens) {
            entityManager.persist(entretien);
        }
    }

    private DBEntretien buildEntretien(Map<String, String> entry) {
        return DBEntretien.of(
                savedCandidats.get(0),
                savedRecruteurs.get(0),
                LocalDateTime.parse(entry.get("horaire"), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }

    @Quand("on liste les tous les entretiens")
    public void onListeLesTousLesEntretiens() {
        initPath();
        response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("");
    }

    @Alors("on récupères les entretiens suivants")
    public void onRécupèresLesEntretiensSuivants(DataTable dataTable) {
        List<EntretienDetailDto> entretiens = dataTableTransformEntries(dataTable, this::buildEntretienDetail);

        EntretienDetailDto[] detailDtos = response.then().extract()
                .as(EntretienDetailDto[].class);
        assertThat(Arrays.stream(detailDtos).toList())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactlyInAnyOrder(entretiens.toArray(EntretienDetailDto[]::new));
    }

    private EntretienDetailDto buildEntretienDetail(Map<String, String> entry) {
        return new EntretienDetailDto(
                Integer.parseInt(entry.get("id")),
                entry.get("candidat"),
                entry.get("recruteur"),
                entry.get("language"),
                LocalDateTime.parse(entry.get("horaire"), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }
}
