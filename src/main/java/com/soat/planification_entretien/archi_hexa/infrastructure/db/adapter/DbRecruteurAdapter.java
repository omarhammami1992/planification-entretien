package com.soat.planification_entretien.archi_hexa.infrastructure.db.adapter;

import com.soat.planification_entretien.archi_hexa.domain.entity.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.port.RecruteurPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.model.DbRecruteur;
import com.soat.planification_entretien.archi_hexa.infrastructure.db.repository.RecruteurRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DbRecruteurAdapter implements RecruteurPort {

    private final RecruteurRepository recruteurRepository;

    public DbRecruteurAdapter(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    @Override
    public Integer save(Recruteur recruteur) {
        DbRecruteur dbRecruteur = toDbRecruteur(recruteur);
        final DbRecruteur savedDbRecruteur = recruteurRepository.save(dbRecruteur);
        return savedDbRecruteur.getId();
    }

    private static DbRecruteur toDbRecruteur(Recruteur recruteur) {
        return new DbRecruteur(recruteur.language(), recruteur.email(), recruteur.experienceEnAnnees());
    }

    @Override
    public Optional<Recruteur> findById(int recruteurId) {
        final Optional<DbRecruteur> optionalDbRecruteur = recruteurRepository.findById(recruteurId);
        return optionalDbRecruteur.map(DbRecruteurAdapter::toRecruteur);
    }

    private static Recruteur toRecruteur(DbRecruteur dbRecruteur) {
        return new Recruteur(dbRecruteur.getId(), dbRecruteur.getLanguage(), dbRecruteur.getEmail(), dbRecruteur.getExperienceInYears());
    }

    @Override
    public List<Recruteur> findByXp(Integer experience) {
        final List<DbRecruteur> dbRecruteurs = recruteurRepository.findAllByExperienceInYearsGreaterThan(experience);
        return dbRecruteurs.stream()
                .map(DbRecruteurAdapter::toRecruteur)
                .toList();
    }
}
