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
    private Integer id;
    private String definition;
    private String example;
    private Instant dateAdded = Instant.now();
    private String userAdded;

    private String countryUse;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "def_id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull
    @Lob
    @Column(name = "definition", nullable = false)
    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    @Size(max = 255)
    @Column(name = "example")
    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    @NotNull
    @Column(name = "date_added", nullable = false)
    public Instant getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Instant dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Size(max = 45)
    @Column(name = "user_added", length = 45)
    public String getUserAdded() {
        return userAdded;
    }

    public void setUserAdded(String userAdded) {
        this.userAdded = userAdded;
    }

    @Size(max = 45)
    @Column(name = "country_use", length = 45)
    public String getCountryUse() {
        return countryUse;
    }

    public void setCountryUse(String countryUse) {
        this.countryUse = countryUse;
    }

}