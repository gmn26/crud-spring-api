package com.gmn26.crud.spring.api.service;

import com.gmn26.crud.spring.api.dto.barang.BarangResponse;
import com.gmn26.crud.spring.api.dto.barang.CreateBarangDto;
import com.gmn26.crud.spring.api.entity.Barang;
import com.gmn26.crud.spring.api.repository.BarangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BarangServiceImpl implements BarangService {

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
        Boolean checkDuplicate = barangRepository.existsBarangByKodeBarang(request.getKodeBarang());

        if (checkDuplicate) {
            return null;
        }

        Barang barangEntity = new Barang();
        barangEntity.setKodeBarang(request.getKodeBarang());
        barangEntity.setNamaBarang(request.getNamaBarang());
        barangEntity.setJumlahStok(request.getJumlahStok());
        barangEntity.setHargaSatuan(request.getHargaSatuan());
        barangEntity.setTanggalMasuk(LocalDateTime.now());

        barangRepository.save(barangEntity);

        return toBarangResponse(barangEntity);
    }

    public BarangResponse update(Long id, CreateBarangDto request) {
        Boolean checkExist = barangRepository.existsById(id);

        if(checkExist) {
            Barang barang = new Barang();
            barang.setKodeBarang(request.getKodeBarang());
            barang.setNamaBarang(request.getNamaBarang());
            barang.setJumlahStok(request.getJumlahStok());
            barang.setHargaSatuan(request.getHargaSatuan());
            barangRepository.save(barang);

            return toBarangResponse(barang);
        } else {
            return null;
        }
    }

    public BarangResponse delete(Long id) {
        Barang barangEntity = barangRepository.findById(id).orElseThrow(() -> new RuntimeException("Barang not found"));

        if (barangEntity != null) {
            barangRepository.delete(barangEntity);
            return toBarangResponse(barangEntity);
        }
        return null;
    }

    public List<Barang> findBarangByJumlahStok(Integer jumlahStok) {
        return null;
    }
}
