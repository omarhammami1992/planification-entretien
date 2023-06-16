package com.soat.planification_entretien.archi_hexa.application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record CandidatRequest(String language, String email, String experienceEnAnnees) {

    public Boolean isValid() {
        return this.language().isBlank() || !isEmail(this.email()) || this.experienceEnAnnees().isBlank() || Integer.parseInt(this.experienceEnAnnees()) < 0;
    }
    private static boolean isEmail(String adresse) {
        String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }
}
