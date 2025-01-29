package com.gmn26.crud.spring.api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "barang")
public class Barang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String kodeBarang;

    private String namaBarang;
    private Integer jumlahStok;
    private BigDecimal hargaSatuan;
    private LocalDateTime tanggalMasuk;
}
