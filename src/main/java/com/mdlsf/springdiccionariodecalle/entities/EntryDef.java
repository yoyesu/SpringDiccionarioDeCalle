package com.mdlsf.springdiccionariodecalle.entities;

import javax.persistence.*;

@Entity
@Table(name = "entry_def")
public class EntryDef {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EmbeddedId
    private EntryDefId id;

    @MapsId("entryId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entry_id", nullable = false)
    private Entry entry;

    @MapsId("defId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "def_id", nullable = false)
    private Definition def;

    public EntryDefId getId() {
        return id;
    }

    public void setId(EntryDefId id) {
        this.id = id;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public Definition getDef() {
        return def;
    }

    public void setDef(Definition def) {
        this.def = def;
    }

}