package com.gmn26.crud.spring.api.bean.barang;

import com.gmn26.crud.spring.api.constant.ValidationMessage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBarangDto {

    @NotNull(message = ValidationMessage.KODE_BARANG_REQUIRED)
    @NotEmpty(message = ValidationMessage.KODE_BARANG_REQUIRED)
    private String kodeBarang;

    @NotNull(message = ValidationMessage.NAMA_BARANG_REQUIRED)
    @NotEmpty(message = ValidationMessage.NAMA_BARANG_REQUIRED)
    private String namaBarang;

    @NotNull(message = ValidationMessage.STOK_BARANG_REQUIRED)
    @NotEmpty(message = ValidationMessage.STOK_BARANG_REQUIRED)
    @Positive(message = "Jumlah Stok harus lebih besar dari 0")
    private Integer jumlahStok;

    @NotNull(message = ValidationMessage.HARGA_SATUAN_REQUIRED)
    @NotEmpty(message = ValidationMessage.HARGA_SATUAN_REQUIRED)
    @Positive(message = "Harga Satuan harus lebih besar dari 0")
    private BigDecimal hargaSatuan;
}
