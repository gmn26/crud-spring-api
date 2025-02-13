package com.gmn26.crud.spring.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "kecamatan")
public class KecamatanEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String namaKecamatan;

    @OneToMany(mappedBy = "kecamatan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KelurahanEntity> kelurahan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idKota")
    private KotaEntity kota;
}
