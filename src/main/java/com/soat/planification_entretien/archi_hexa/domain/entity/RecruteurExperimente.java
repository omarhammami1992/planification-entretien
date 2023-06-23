
package com.soat.planification_entretien.archi_hexa.domain.entity;

public final class RecruteurExperimente {

    private final String competence;
    private final Integer id;
    private final String email;

    public RecruteurExperimente(Recruteur recruteur) {
        this.id = recruteur.id();
        this.competence = recruteur.experienceEnAnnees() + " ans en " + recruteur.language();
        this.email = recruteur.email();
    }

    public String getCompetence() {
        return competence;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
