package com.gmn26.crud.spring.api.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BarangRequest {
    private String kodeBarang;
    private String namaBarang;
    private Integer jumlahStok;
    private BigDecimal hargaSatuan;
    private LocalDateTime tanggalMasuk;
}
