package com.soat.planification_entretien.archi_hexa.domain.entity;

import com.soat.planification_entretien.archi_hexa.domain.exception.InvalideEntretienParams;

import java.time.LocalDateTime;

public class Entretien {
    private final Candidat candidat;
    private final Recruteur recruteur;
    private final LocalDateTime horaireEntretien;

    public Entretien(Candidat candidat, Recruteur recruteur, LocalDateTime dateEtHeureDisponibiliteDuRecruteur, LocalDateTime dateEtHeureDisponibiliteDuCandidat) {
        if (!isRecruteurEtCandidatValide(candidat, recruteur, dateEtHeureDisponibiliteDuRecruteur, dateEtHeureDisponibiliteDuCandidat)) {
            throw new InvalideEntretienParams();
        }
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaireEntretien = dateEtHeureDisponibiliteDuRecruteur;
    }

    private static boolean isRecruteurEtCandidatValide(Candidat candidat, Recruteur recruteur, LocalDateTime dateEtHeureDisponibiliteDuRecruteur, LocalDateTime dateEtHeureDisponibiliteDuCandidat) {
        return recruteur.getLanguage().equals(candidat.getLanguage())
                && recruteur.getExperienceEnAnnees() > candidat.getExperienceEnAnnees()
                && dateEtHeureDisponibiliteDuCandidat.equals(dateEtHeureDisponibiliteDuRecruteur);
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public Recruteur getRecruteur() {
        return recruteur;
    }

    public LocalDateTime getHoraireEntretien() {
        return horaireEntretien;
    }
}
