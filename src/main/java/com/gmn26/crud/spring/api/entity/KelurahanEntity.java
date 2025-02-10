package com.gmn26.crud.spring.api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "kelurahan")
public class KelurahanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String namaKelurahan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idKecamatan")
    private KecamatanEntity kecamatan;

}
