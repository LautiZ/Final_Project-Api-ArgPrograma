package com.cursojava.project.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
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
