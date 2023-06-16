package com.soat.planification_entretien.archi_hexa.infrastructure.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class DbEntretien {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "candidat_id")
    private DbCandidat candidat;

    @Column
    private LocalDateTime horaireEntretien;

    @ManyToOne
    @JoinColumn(name = "recruteur_id")
    private DbRecruteur recruteur;

    private DbEntretien(DbCandidat candidat, DbRecruteur recruteur, LocalDateTime horaire) {
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaireEntretien = horaire;
    }

    public DbEntretien() {

    }

    public static DbEntretien of(DbCandidat candidat, DbRecruteur recruteur, LocalDateTime horaire) {
        return new DbEntretien(candidat, recruteur, horaire);
    }

    public DbCandidat getCandidat() {
        return candidat;
    }

    public DbRecruteur getRecruteur() {
        return recruteur;
    }

    public LocalDateTime getHoraireEntretien() {
        return horaireEntretien;
    }

    public Integer getId() {
        return id;
    }
}
