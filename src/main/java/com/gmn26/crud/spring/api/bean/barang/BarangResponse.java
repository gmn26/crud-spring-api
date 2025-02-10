package com.gmn26.crud.spring.api.bean.barang;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BarangResponse {
    private Long id;
    private String kodeBarang;
    private String namaBarang;
    private Integer jumlahStok;
    private BigDecimal hargaSatuan;
    private LocalDateTime tanggalMasuk;
    private UUID idUser;
}
