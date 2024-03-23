package com.mdlsf.springdiccionariodecalle.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EntryId implements Serializable {
    private static final long serialVersionUID = 2969364269986936979L;
    @NotNull
    @Column(name = "entry_id", nullable = false)
    private Integer entryId;

    @NotNull
    @Column(name = "def_id", nullable = false)
    private Integer defId;

    public Integer getEntryId() {
        return entryId;
    }

    public void setEntryId(Integer entryId) {
        this.entryId = entryId;
    }

    public Integer getDefId() {
        return defId;
    }

    public void setDefId(Integer defId) {
        this.defId = defId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EntryId entity = (EntryId) o;
        return Objects.equals(this.defId, entity.defId) &&
                Objects.equals(this.entryId, entity.entryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(defId, entryId);
    }

}