package com.gmn26.crud.spring.api.service;

import com.gmn26.crud.spring.api.dto.BarangResponse;
import com.gmn26.crud.spring.api.dto.CreateBarangDto;
import com.gmn26.crud.spring.api.entity.Barang;
import com.gmn26.crud.spring.api.repository.BarangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BarangService {
    @Autowired
    private BarangRepository barangRepository;

    private BarangResponse toBarangResponse(Barang barang) {
        return BarangResponse.builder()
                .id(barang.getId())
                .kodeBarang(barang.getKodeBarang())
                .namaBarang(barang.getNamaBarang())
                .jumlahStok(barang.getJumlahStok())
                .hargaSatuan(barang.getHargaSatuan())
                .tanggalMasuk(barang.getTanggalMasuk())
                .build();
    }

    public List<BarangResponse> listAll() {
        List<Barang> barangs = barangRepository.findAll();
        return barangs.stream().map(this::toBarangResponse).collect(Collectors.toList());
    }

    public BarangResponse create(CreateBarangDto request) {
        Barang barangEntity = new Barang();
        barangEntity.setKodeBarang(request.getKodeBarang());
        barangEntity.setNamaBarang(request.getNamaBarang());
        barangEntity.setJumlahStok(request.getJumlahStok());
        barangEntity.setHargaSatuan(request.getHargaSatuan());
        barangEntity.setTanggalMasuk(request.getTanggalMasuk());

        barangRepository.save(barangEntity);

        return toBarangResponse(barangEntity);
    }
}
