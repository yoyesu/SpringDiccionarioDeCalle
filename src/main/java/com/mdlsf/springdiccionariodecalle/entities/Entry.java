package com.mdlsf.springdiccionariodecalle.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Set;

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
    @EmbeddedId
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @MapsId("entryId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "entry_id", nullable = false)
    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    @MapsId("defId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_added")
    public String getUserAdded() {
        return userAdded;
    }

    public void setUserAdded(String userAdded) {
        this.userAdded = userAdded;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public Set<String> getCountryUse() {
        return countryUse;
    }

    public void setCountryUse(Set<String> countryUse) {
        this.countryUse = countryUse;
    }

}