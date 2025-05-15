package com.soat.planification_entretien.archi_hexa.infrastructure.db.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class DBEntretien {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "candidat_id")
    private DBCandidat candidat;

    @Column
    private LocalDateTime horaireEntretien;

    @ManyToOne
    @JoinColumn(name = "recruteur_id")
    private DBRecruteur recruteur;

    private DBEntretien(DBCandidat candidat, DBRecruteur recruteur, LocalDateTime horaire) {
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaireEntretien = horaire;
    }

    public DBEntretien() {

    }

    public static DBEntretien of(DBCandidat candidat, DBRecruteur recruteur, LocalDateTime horaire) {
        return new DBEntretien(candidat, recruteur, horaire);
    }

    public DBCandidat getCandidat() {
        return candidat;
    }

    public DBRecruteur getRecruteur() {
        return recruteur;
    }

    public LocalDateTime getHoraireEntretien() {
        return horaireEntretien;
    }

    public Integer getId() {
        return id;
    }
}
