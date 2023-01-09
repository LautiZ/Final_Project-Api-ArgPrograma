package com.cursojava.project.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "AboutmeCrud")
public class Aboutme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    @Column(name = "aboutme_id")
    private Long aboutmeId;

    /* Agregar imagen a futuro
    @Getter @Setter
    @Column(name = "image")
    private String image;
     */

    @Getter @Setter
    @Column(name = "text", columnDefinition="TEXT")
    private String text;

    @Getter @Setter
    @Column(name = "linklinkedin")
    private String linklinkedin;
}
