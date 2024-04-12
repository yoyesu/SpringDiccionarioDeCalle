package com.mdlsf.springdiccionariodecalle.entities;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column(name = "term", nullable = false)
    private String term;

    @Column(name = "def", nullable = false)
    private String def;

    @NotNull
    @Column(name = "last_updated", nullable = false)
    @Builder.Default
    private Instant lastUpdated = Instant.now();

    @NotNull
    @Column(name = "date_added", nullable = false)
    @Builder.Default
    private Instant dateAdded = Instant.now();

    @Builder.Default
    private String userAdded = "admin";
    private Set<String> countryUse;


}