package com.soat.planification_entretien.archi_hexa.infrastructure.database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RecruteurEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String language;
    @Column
    private String email;
    @Column
    private Integer experienceInYears;

    public RecruteurEntity(String language, String email, int experienceInYears) {
        this.language = language;
        this.email = email;
        this.experienceInYears = experienceInYears;
    }

    public RecruteurEntity(Integer id, String language, String email, Integer experienceInYears) {
        this.id = id;
        this.language = language;
        this.email = email;
        this.experienceInYears = experienceInYears;
    }

    public RecruteurEntity() {

    }

    public Integer getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public String getEmail() {
        return email;
    }

    public Integer getExperienceInYears() {
        return experienceInYears;
    }
}
