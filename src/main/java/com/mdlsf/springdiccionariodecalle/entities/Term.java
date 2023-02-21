package com.mdlsf.springdiccionariodecalle.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.web.servlet.function.ServerRequest;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "terms")
public class Term {
    private Integer id;

    private String entryName;


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



    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id", scope= ServerRequest.class)
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "entries",
            joinColumns = @JoinColumn(name = "entry_id"),
            inverseJoinColumns = @JoinColumn(name = "def_id"))
    public Set<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(Set<Definition> definitions) {
        this.definitions = definitions;
    }

}