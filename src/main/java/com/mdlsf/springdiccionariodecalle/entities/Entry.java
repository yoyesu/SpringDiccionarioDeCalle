package com.mdlsf.springdiccionariodecalle.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "entries")
public class Entry {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EmbeddedId
    private EntryId id;

    @MapsId("entryId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entry_id", nullable = false)
    private Term term;
//TODO add last updated field (if any update is made to the entry or the definition, this date should be updated)
    @MapsId("defId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "def_id", nullable = false)
    private Definition def;

    @NotNull
    @Column(name = "last_updated", nullable = false)
    private Instant lastUpdated = Instant.now();

    public EntryId getId() {
        return id;
    }

    public void setId(EntryId id) {
        this.id = id;
    }

    public Term getEntry() {
        return term;
    }

    public void setEntry(Term term) {
        this.term = term;
    }

    public Definition getDef() {
        return def;
    }

    public void setDef(Definition def) {
        this.def = def;
    }
    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}