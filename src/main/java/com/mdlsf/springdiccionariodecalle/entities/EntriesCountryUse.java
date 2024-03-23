package com.mdlsf.springdiccionariodecalle.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "entries_country_use")
public class EntriesCountryUse {

    private EntriesCountryUseId id;

    private Term entryEntryId;
    private Definition entryDefId;
    private Country countryUseCountriesId;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EmbeddedId
    public EntriesCountryUseId getId() {
        return id;
    }

    public void setId(EntriesCountryUseId id) {
        this.id = id;
    }

    @MapsId("entryId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "entry_id", nullable = false)
    public Term getEntryEntryId() {
        return entryEntryId;
    }

    public void setEntryEntryId(Term entryEntryId) {
        this.entryEntryId = entryEntryId;
    }

    @MapsId("defId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "def_id", nullable = false)
    public Definition getEntryDefId() {
        return entryDefId;
    }

    public void setEntryDefId(Definition entryDefId) {
        this.entryDefId = entryDefId;
    }

    @MapsId("country")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "country_use_countries_id", nullable = false)
    public Country getCountryUseCountriesId() {
        return countryUseCountriesId;
    }

    public void setCountryUseCountriesId(Country countryUseCountriesId) {
        this.countryUseCountriesId = countryUseCountriesId;
    }
}
