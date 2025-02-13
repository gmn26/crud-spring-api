package com.gmn26.crud.spring.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "provinsi")
public class ProvinsiEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String namaProvinsi;

    @OneToMany(mappedBy = "provinsi", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KotaEntity> listKota;

}
