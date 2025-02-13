package com.gmn26.crud.spring.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "kota")
public class KotaEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String namaKota;

    @OneToMany(mappedBy = "kota", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<KecamatanEntity> kecamatan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idProvinsi", nullable = false)
    private ProvinsiEntity provinsi;

}
