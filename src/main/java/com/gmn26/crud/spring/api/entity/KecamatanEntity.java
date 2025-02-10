package com.gmn26.crud.spring.api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "kecamatan")
public class KecamatanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String namaKecamatan;

    @OneToMany(mappedBy = "kecamatan", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KelurahanEntity> kelurahan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idKota")
    private KotaEntity kota;
}
