package com.soat.planification_entretien;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.soat.ATest;
import com.soat.planification_entretien.archi_hexa.application.EntretienController;
import com.soat.planification_entretien.archi_hexa.application.EntretienDetailResponse;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.model.DbCandidat;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.model.DbEntretien;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.model.DbRecruteur;
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

    private List<DbCandidat> savedCandidats = new ArrayList<>();
    private List<DbRecruteur> savedRecruteurs = new ArrayList<>();

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
        List<DbRecruteur> recruteurs = dataTableTransformEntries(dataTable, this::buildRecruteur);

        for (DbRecruteur recruteur : recruteurs) {
            DbRecruteur saved = entityManager.persist(recruteur);
            savedRecruteurs.add(saved);
        }
    }

    private DbRecruteur buildRecruteur(Map<String, String> entry) {
        return new DbRecruteur(
                entry.get("language"),
                entry.get("email"),
                Integer.parseInt(entry.get("xp")));
    }

    @Et("les candidats existants")
    public void lesCandidatsExistants(DataTable dataTable) {
        List<DbCandidat> candidats = dataTableTransformEntries(dataTable, this::buildCandidat);

        for (DbCandidat candidat : candidats) {
            DbCandidat saved = entityManager.persist(candidat);
            savedCandidats.add(saved);
        }
    }

    private DbCandidat buildCandidat(Map<String, String> entry) {
        return new DbCandidat(
                entry.get("language"),
                entry.get("email"),
                Integer.parseInt(entry.get("xp")));
    }

    @Et("les entretiens existants")
    public void lesEntretiensExistants(DataTable dataTable) {
        List<DbEntretien> entretiens = dataTableTransformEntries(dataTable, this::buildEntretien);

        for (DbEntretien entretien : entretiens) {
            entityManager.persist(entretien);
        }
    }

    private DbEntretien buildEntretien(Map<String, String> entry) {
        return DbEntretien.of(
                savedCandidats.get(0),
                savedRecruteurs.get(0),
                LocalDateTime.parse(entry.get("horaire"), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }

    @Quand("on liste les tous les entretiens")
    public void onListeLesTousLesEntretiens() {
        //@formatter:off
        response = given()
                        .contentType(ContentType.JSON)
                .when()
                        .get(EntretienController.PATH);
        //@formatter:on

    }

    @Alors("on récupères les entretiens suivants")
    public void onRécupèresLesEntretiensSuivants(DataTable dataTable) {
        List<EntretienDetailResponse> entretiens = dataTableTransformEntries(dataTable, this::buildEntretienDetail);

        EntretienDetailResponse[] detailDtos = response.then().extract()
                .as(EntretienDetailResponse[].class);
        assertThat(Arrays.stream(detailDtos).toList())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactlyInAnyOrder(entretiens.toArray(EntretienDetailResponse[]::new));
    }

    private EntretienDetailResponse buildEntretienDetail(Map<String, String> entry) {
        return new EntretienDetailResponse(
                Integer.parseInt(entry.get("id")),
                entry.get("candidat"),
                entry.get("recruteur"),
                entry.get("language"),
                LocalDateTime.parse(entry.get("horaire"), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }
}
