package com.soat.planification_entretien.archi_hexa.domain.entity;

import com.soat.planification_entretien.archi_hexa.domain.exception.InvalidParams;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Recruteur {

    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    private final String language;
    private final String email;
    private final Integer experienceEnAnnees;

    public Recruteur(String language, String email, Integer experienceEnAnnees) {
        if (language.isBlank() || !isEmailValid(email) || experienceEnAnnees < 0) {
            throw new InvalidParams();
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

    public Integer getExperienceEnAnnees() {
        return experienceEnAnnees;
    }

    private boolean isEmailValid(String email) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(email);
        return m.matches();
    }
}
