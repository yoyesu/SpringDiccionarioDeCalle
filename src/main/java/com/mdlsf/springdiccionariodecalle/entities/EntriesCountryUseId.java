package com.mdlsf.springdiccionariodecalle.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class EntriesCountryUseId implements Serializable {

    private static final long serialVersionUID = 2969364269986936979L;
    @NotNull
    @Column(name = "entry_id", nullable = false)
    private Integer entryId;

    @NotNull
    @Column(name = "def_id", nullable = false)
    private Integer defId;

    @NotNull
    @Column(name = "country", nullable = false)
    private Integer country;

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

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }
}
