package com.gmn26.crud.spring.api.service.barang;

import com.gmn26.crud.spring.api.dto.barang.BarangResponse;
import com.gmn26.crud.spring.api.dto.barang.CreateBarangDto;
import com.gmn26.crud.spring.api.entity.BarangEntity;
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

    private BarangResponse toBarangResponse(BarangEntity barangEntity) {
        return BarangResponse.builder()
                .id(barangEntity.getId())
                .kodeBarang(barangEntity.getKodeBarang())
                .namaBarang(barangEntity.getNamaBarang())
                .jumlahStok(barangEntity.getJumlahStok())
                .hargaSatuan(barangEntity.getHargaSatuan())
                .tanggalMasuk(barangEntity.getTanggalMasuk())
                .build();
    }

    public List<BarangResponse> listAll() {
        List<BarangEntity> barangEntities = barangRepository.findAll();
        return barangEntities.stream().map(this::toBarangResponse).collect(Collectors.toList());
    }

    public BarangResponse create(CreateBarangDto request) {
        Boolean checkDuplicate = barangRepository.existsBarangByKodeBarang(request.getKodeBarang());

        if (checkDuplicate) {
            return null;
        }

        BarangEntity barangEntity = new BarangEntity();
        barangEntity.setKodeBarang(request.getKodeBarang());
        barangEntity.setNamaBarang(request.getNamaBarang());
        barangEntity.setJumlahStok(request.getJumlahStok());
        barangEntity.setHargaSatuan(request.getHargaSatuan());
        barangEntity.setTanggalMasuk(LocalDateTime.now());

        barangRepository.save(barangEntity);

        return toBarangResponse(barangEntity);
    }

    public BarangResponse update(Long id, CreateBarangDto request) {
        boolean checkExist = barangRepository.existsById(id);

        if(checkExist) {
            BarangEntity barangEntity = new BarangEntity();
            barangEntity.setKodeBarang(request.getKodeBarang());
            barangEntity.setNamaBarang(request.getNamaBarang());
            barangEntity.setJumlahStok(request.getJumlahStok());
            barangEntity.setHargaSatuan(request.getHargaSatuan());
            barangRepository.save(barangEntity);

            return toBarangResponse(barangEntity);
        } else {
            return null;
        }
    }

    public BarangResponse delete(Long id) {
        boolean checkExist = barangRepository.existsById(id);

        if (checkExist) {
            BarangEntity barangEntity = new BarangEntity();
            barangRepository.deleteById(id);
            return toBarangResponse(barangEntity);
        }
        return null;
    }

    public List<BarangEntity> findBarangByJumlahStok(Integer jumlahStok) {
        return null;
    }
}
