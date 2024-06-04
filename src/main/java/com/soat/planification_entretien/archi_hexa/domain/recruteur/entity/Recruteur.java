package com.soat.planification_entretien.archi_hexa.domain.recruteur.entity;

import com.soat.planification_entretien.archi_hexa.domain.recruteur.exception.InvalideRecruteurParams;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Recruteur {

    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    private final String language;
    private final String email;
    private final int experienceEnAnnees;

    public Recruteur(String language, String email, int experienceEnAnnees) {
        if (isaValid(language, email, experienceEnAnnees)) {
            throw new InvalideRecruteurParams();
        }
        this.language = language;
        this.email = email;
        this.experienceEnAnnees = experienceEnAnnees;
    }

    public String getLanguage() {
        return language;
    }

    public String getEmail() {
        return email;
    }

    public int getExperienceEnAnnees() {
        return experienceEnAnnees;
    }


    private static boolean isaValid(String language, String email, int experienceEnAnnees) {
        return language.isBlank() || !isEmail(email) || experienceEnAnnees < 0;
    }

    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }
}
