package com.mdlsf.springdiccionariodecalle.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "entries")
public class Entry {

    private EntryId id;
    private Term term;
    private Definition def;
    private Instant lastUpdated = Instant.now();
    private Instant dateAdded = Instant.now();
    private User userAdded;
    private Set<Country> countryUse;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EmbeddedId
    public EntryId getId() {
        return id;
    }

    public void setId(EntryId id) {
        this.id = id;
    }

    @MapsId("entryId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "entry_id", nullable = false)
    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    @MapsId("defId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "def_id", nullable = false)
    public Definition getDef() {
        return def;
    }

    public void setDef(Definition def) {
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
    public User getUserAdded() {
        return userAdded;
    }

    public void setUserAdded(User userAdded) {
        this.userAdded = userAdded;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "entries_country_use")
    public Set<Country> getCountryUse() {
        return countryUse;
    }

    public void setCountryUse(Set<Country> countryUse) {
        this.countryUse = countryUse;
    }

}