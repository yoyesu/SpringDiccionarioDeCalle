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
    private LocalDate dateAdded;

    @Size(max = 45)
    @Column(name = "user_added", length = 45)
    private String userAdded;

    @Size(max = 45)
    @Column(name = "country_use", length = 45)
    private String countryUse;

    @Column(name = "year_use")
    private Integer yearUse;

    @NotNull
    @Column(name = "last_updated", nullable = false)
    private Instant lastUpdated;

    @ManyToMany
    @JoinTable(name = "entry_def",
            joinColumns = @JoinColumn(name = "def_id"),
            inverseJoinColumns = @JoinColumn(name = "entry_id"))
    private Set<Entry> entries = new LinkedHashSet<>();

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

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
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

    public Integer getYearUse() {
        return yearUse;
    }

    public void setYearUse(Integer yearUse) {
        this.yearUse = yearUse;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Set<Entry> getEntries() {
        return entries;
    }

    public void setEntries(Set<Entry> entries) {
        this.entries = entries;
    }

}