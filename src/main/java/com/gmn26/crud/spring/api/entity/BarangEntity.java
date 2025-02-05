package com.gmn26.crud.spring.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Data
@Table(name = "barang")
public class BarangEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String kodeBarang;

    private String namaBarang;
    private Integer jumlahStok;
    private BigDecimal hargaSatuan;
    private LocalDateTime tanggalMasuk;
    private UUID idUser;

//    @ManyToOne
//    @JoinColumn(name = "userId", nullable = false)
//    private UserEntity users;
}
