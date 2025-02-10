package com.gmn26.crud.spring.api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "kota")
public class KotaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String namaKota;

    @OneToMany(mappedBy = "kota", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<KecamatanEntity> kecamatan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProvinsi")
    private ProvinsiEntity provinsi;

}
