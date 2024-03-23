package com.mdlsf.springdiccionariodecalle.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "terms")
public class Term {
    private Integer id;

    private String entryName;


//    private Set<Definition> definitions = new LinkedHashSet<>();

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



//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id", scope= ServerRequest.class)
//    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
//    @JoinTable(name = "entries",
//            joinColumns = @JoinColumn(name = "entry_id"),
//            inverseJoinColumns = @JoinColumn(name = "def_id"))
//    public Set<Definition> getDefinitions() {
//        return definitions;
//    }
//
//    public void setDefinitions(Set<Definition> definitions) {
//        this.definitions = definitions;
//    }

}