package com.mdlsf.springdiccionariodecalle.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.Set;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "entries")
public class Entry {

    private Integer id;
    private String term;
    private String def;
    private Instant lastUpdated = Instant.now();
    private Instant dateAdded = Instant.now();
    private String userAdded;
    private Set<String> countryUse;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }


    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    @NotNull
    @Column(name = "last_updated", nullable = false)
    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @NotNull
    @Column(name = "date_added", nullable = false)
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

    public Set<String> getCountryUse() {
        return countryUse;
    }

    public void setCountryUse(Set<String> countryUse) {
        this.countryUse = countryUse;
    }

}