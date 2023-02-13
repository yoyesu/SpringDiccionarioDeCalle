package com.mdlsf.springdiccionariodecalle.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.web.servlet.function.ServerRequest;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "entries")
public class Entry {
    private Integer id;

    private String entryName;

    private Instant lastUpdated;


    private Set<Definition> definitions = new LinkedHashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entry_id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Size(max = 100)
    @NotNull
    @Column(name = "entry_name", nullable = false, length = 100)
    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    @NotNull
    @Column(name = "last_updated", nullable = false)
    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id", scope= ServerRequest.class)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "entry_def",
            joinColumns = @JoinColumn(name = "entry_id"),
            inverseJoinColumns = @JoinColumn(name = "def_id"))
    public Set<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(Set<Definition> definitions) {
        this.definitions = definitions;
    }

}