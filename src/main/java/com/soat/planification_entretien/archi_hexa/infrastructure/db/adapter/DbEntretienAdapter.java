package com.soat.planification_entretien.archi_hexa.infrastructure.db.adapter;

import com.soat.planification_entretien.archi_hexa.domain.entity.Candidat;
import com.soat.planification_entretien.archi_hexa.domain.entity.Entretien;
import com.soat.planification_entretien.archi_hexa.domain.entity.EntretienDetail;
import com.soat.planification_entretien.archi_hexa.domain.entity.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.port.EntretienPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.model.DbCandidat;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.model.DbEntretien;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.model.DbRecruteur;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.repository.EntretienRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DbEntretienAdapter implements EntretienPort {

    private final EntretienRepository entretienRepository;

    public DbEntretienAdapter(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    @Override
    public void save(Entretien entretien) {
       DbEntretien dbEntretien =  toDbEntretien(entretien);
       entretienRepository.save(dbEntretien);
    }

    @Override
    public List<EntretienDetail> findAll() {
        final List<DbEntretien> dbEntretiens = entretienRepository.findAll();
        return dbEntretiens.stream()
                .map(DbEntretienAdapter::toEntretienDetail)
                .toList();
    }

    private static EntretienDetail toEntretienDetail(DbEntretien dbEntretien) {
        return new EntretienDetail(
                dbEntretien.getId(),
                dbEntretien.getCandidat().getEmail(),
                dbEntretien.getRecruteur().getEmail(),
                dbEntretien.getRecruteur().getLanguage(),
                dbEntretien.getHoraireEntretien()
        );
    }

    private DbEntretien toDbEntretien(Entretien entretien) {
        final DbCandidat dbCandidat = toDbCandidat(entretien.candidat());
        final DbRecruteur dbRecruteur = toDbRecruteur(entretien.recruteur());
        return DbEntretien.of(dbCandidat,dbRecruteur, entretien.horaireEntretien());
    }

    private static DbCandidat toDbCandidat(Candidat candidat) {
        return new DbCandidat(candidat.id(), candidat.language(), candidat.email(), candidat.experienceEnAnnees());
    }

    private static DbRecruteur toDbRecruteur(Recruteur recruteur) {
        return new DbRecruteur(recruteur.id(), recruteur.language(), recruteur.email(), recruteur.experienceEnAnnees());
    }
}
