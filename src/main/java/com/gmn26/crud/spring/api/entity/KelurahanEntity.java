package com.gmn26.crud.spring.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "kelurahan")
public class KelurahanEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String namaKelurahan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idKecamatan")
    private KecamatanEntity kecamatan;

}
