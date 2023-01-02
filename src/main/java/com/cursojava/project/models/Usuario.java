package com.cursojava.project.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

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

}
