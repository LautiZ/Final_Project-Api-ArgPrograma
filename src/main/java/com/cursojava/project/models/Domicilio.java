package com.cursojava.project.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "DomicilioCrud")
public class Domicilio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Column(name = "domicilio_id")
    private Long domicilioId;

    @Getter @Setter
    @Column(name = "address")
    private String address;

    @Getter @Setter
    @Column(name = "city")
    private String city;

    @Getter @Setter
    @Column(name = "province")
    private String province;

    @Getter @Setter
    @Column(name = "country")
    private String country;
}
