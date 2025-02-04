package com.gmn26.crud.spring.api.dto.barang;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBarangDto {
    private String kodeBarang;
    private String namaBarang;
    private Integer jumlahStok;
    private BigDecimal hargaSatuan;
}
