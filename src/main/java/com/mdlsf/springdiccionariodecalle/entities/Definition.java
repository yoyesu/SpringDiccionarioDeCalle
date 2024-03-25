package com.mdlsf.springdiccionariodecalle.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
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