package com.mdlsf.springdiccionariodecalle.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "definitions")
public class Definition {
    private Integer id;
    private String definition;
    private String example;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "def_id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull
    @Lob
    @Column(name = "definition", nullable = false)
    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    @Size(max = 255)
    @Column(name = "example")
    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

}