package com.mdlsf.springdiccionariodecalle.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "definitions")
public class Definition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "def_id", nullable = false)
    private Integer id;

    @NotNull
    @Lob
    @Column(name = "definition", nullable = false)
    private String definition;

    @Size(max = 255)
    @Column(name = "example")
    private String example;

    @NotNull
    @Column(name = "date_added", nullable = false)
    private Instant dateAdded = Instant.now();

    @Size(max = 45)
    @Column(name = "user_added", length = 45)
    private String userAdded;

    @Size(max = 45)
    @Column(name = "country_use", length = 45)
    private String countryUse;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public Instant getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Instant dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getUserAdded() {
        return userAdded;
    }

    public void setUserAdded(String userAdded) {
        this.userAdded = userAdded;
    }

    public String getCountryUse() {
        return countryUse;
    }

    public void setCountryUse(String countryUse) {
        this.countryUse = countryUse;
    }

}