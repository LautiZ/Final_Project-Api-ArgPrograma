package com.cursojava.project.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Column(name = "id")
    private Long id;

    @Getter @Setter
    @Column(name = "nombre")
    @NotNull
    private String nombre;

    @Getter @Setter
    @Column(name = "apellido")
    @NotNull
    private String apellido;

    @Getter @Setter
    @Column(name = "email")
    @NotNull
    private String email;

    @Getter @Setter
    @Column(name = "telefono")
    private Long telefono;

    @Getter @Setter
    @Column(name = "username")
    @NotNull
    private String username;

    @Getter @Setter
    @Column(name = "password")
    @NotNull
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_domicilio_id")
    private Domicilio domicilio;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_aboutme_id")
    private Aboutme aboutme;

    @JsonManagedReference
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy = "user")
    @Getter @Setter
    private List<Projects> projectsList;

    @JsonManagedReference
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy = "user")
    @Getter @Setter
    private List<Education> educationList;

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public Aboutme getAboutme() {
        return aboutme;
    }

    public void setAboutme(Aboutme aboutme) {
        this.aboutme = aboutme;
    }

}
