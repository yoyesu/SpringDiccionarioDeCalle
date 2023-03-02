package com.mdlsf.springdiccionariodecalle.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Entity
@Table(name = "entries")
public class Entry {

    private EntryId id;

    private Term term;
//TODO add last updated field (if any update is made to the entry or the definition, this date should be updated)
    private Definition def;

    private Instant lastUpdated = Instant.now();

    private Instant dateAdded = Instant.now();
    private User userAdded;

    private Country countryUse;

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

    @OneToOne
    @Size(max = 45)
    @Column(name = "user_added", length = 45)
    public User getUserAdded() {
        return userAdded;
    }

    public void setUserAdded(User userAdded) {
        this.userAdded = userAdded;
    }

    @OneToMany
    @Size(max = 45)
    @Column(name = "country_use", length = 45)
    public Country getCountryUse() {
        return countryUse;
    }

    public void setCountryUse(Country countryUse) {
        this.countryUse = countryUse;
    }

}