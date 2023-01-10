package com.cursojava.project.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "AboutmeCrud")
public class Aboutme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Column(name = "aboutme_id")
    private Long aboutmeId;

    @Getter @Setter
    @Column(name = "text", columnDefinition="TEXT")
    private String text;

    @Getter @Setter
    @Column(name = "linklinkedin")
    private String linklinkedin;
}
