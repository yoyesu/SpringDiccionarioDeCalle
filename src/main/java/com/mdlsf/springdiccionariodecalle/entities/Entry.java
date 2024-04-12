package com.mdlsf.springdiccionariodecalle.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @JsonProperty
    @Column(name = "term", nullable = false)
    private String term;

    @JsonProperty
    @Column(name = "def", nullable = false)
    private String def;

    @JsonProperty
    @NotNull
    @Column(name = "last_updated", nullable = false)
    @Builder.Default
    private Instant lastUpdated = Instant.now();

    @JsonProperty
    @NotNull
    @Column(name = "date_added", nullable = false)
    @Builder.Default
    private Instant dateAdded = Instant.now();

    @JsonProperty
    @Builder.Default
    private String userAdded = "admin";
    private Set<String> countryUse;


}