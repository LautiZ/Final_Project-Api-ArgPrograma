package com.cursojava.project.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "EducationCrud")
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Column(name = "education_id")
    private Long educationId;

    @Getter @Setter
    @Column(name = "title")
    private String title;

    @Getter @Setter
    @Column(name = "subtitle")
    private String subtitle;

    @Getter @Setter
    @Column(name = "text", columnDefinition="TEXT")
    private String text;

    @Getter @Setter
    @Column(name = "date", columnDefinition="DATE")
    private String date;

    @JsonBackReference
    @ManyToOne
    @Getter @Setter
    private Usuario user;

}
