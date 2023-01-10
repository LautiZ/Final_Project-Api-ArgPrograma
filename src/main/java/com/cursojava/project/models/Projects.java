package com.cursojava.project.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "ProjectsCrud")
public class Projects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Column(name = "projects_id")
    private Long projectsId;

    @Getter @Setter
    @Column(name = "title")
    private String title;

    @Getter @Setter
    @Column(name = "text", columnDefinition="TEXT")
    private String text;

    @Getter @Setter
    @Column(name = "link")
    private String link;

    @JsonBackReference
    @ManyToOne
    @Getter @Setter
    private Usuario user;

}
