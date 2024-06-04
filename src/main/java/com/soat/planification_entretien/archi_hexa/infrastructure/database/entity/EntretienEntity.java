package com.soat.planification_entretien.archi_hexa.infrastructure.database.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class EntretienEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "candidat_id")
    private CandidatEntity candidat;

    @Column
    private LocalDateTime horaireEntretien;

    @ManyToOne
    @JoinColumn(name = "recruteur_id")
    private RecruteurEntity recruteur;

    private EntretienEntity(CandidatEntity candidat, RecruteurEntity recruteur, LocalDateTime horaire) {
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaireEntretien = horaire;
    }

    public EntretienEntity() {

    }

    public static EntretienEntity of(CandidatEntity candidat, RecruteurEntity recruteur, LocalDateTime horaire) {
        return new EntretienEntity(candidat, recruteur, horaire);
    }

    public CandidatEntity getCandidat() {
        return candidat;
    }

    public RecruteurEntity getRecruteur() {
        return recruteur;
    }

    public LocalDateTime getHoraireEntretien() {
        return horaireEntretien;
    }

    public Integer getId() {
        return id;
    }
}
