package com.mdlsf.springdiccionariodecalle.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "countries")
public class Country {

    private int countriesId;
    private String country;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "countries_id", nullable = false)
    public int getCountriesId() {
        return countriesId;
    }

    public void setCountriesId(int countriesId) {
        this.countriesId = countriesId;
    }

    @NotNull
    @Column(name = "country", nullable = false)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
