package com.gmn26.crud.spring.api.service;

import com.gmn26.crud.spring.api.bean.barang.BarangResponse;
import com.gmn26.crud.spring.api.bean.barang.CreateBarangDto;
import com.gmn26.crud.spring.api.entity.BarangEntity;
import com.gmn26.crud.spring.api.entity.UserEntity;
import com.gmn26.crud.spring.api.repository.BarangRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BarangService {

    private final BarangRepository barangRepository;

    private final ValidationService validationService;

    private BarangResponse toBarangResponse(BarangEntity barangEntity) {
        return BarangResponse.builder()
                .id(barangEntity.getId())
                .idUser(barangEntity.getIdUser())
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

    public List<BarangResponse> listAuthedBarang(UserEntity user) {
        List<BarangEntity> barangEntities = barangRepository.findByIdUser(user.getId());
        return barangEntities.stream().map(this::toBarangResponse).collect(Collectors.toList());
    }

    public BarangResponse create(UserEntity user, CreateBarangDto request) {
        validationService.validate(request);

        Boolean checkDuplicate = barangRepository.existsBarangByKodeBarang(request.getKodeBarang());

        if (checkDuplicate) {
            return null;
        }

        log.info("Id User Yang login: {}", user.getId());

        BarangEntity barangEntity = new BarangEntity();
        barangEntity.setIdUser(user.getId());
        barangEntity.setKodeBarang(request.getKodeBarang());
        barangEntity.setNamaBarang(request.getNamaBarang());
        barangEntity.setJumlahStok(request.getJumlahStok());
        barangEntity.setHargaSatuan(request.getHargaSatuan());
        barangEntity.setTanggalMasuk(LocalDateTime.now());

        barangRepository.save(barangEntity);

        return toBarangResponse(barangEntity);
    }

    public BarangResponse update(Long id, UserEntity user ,CreateBarangDto request) {
        validationService.validate(request);

        boolean checkExist = barangRepository.existsById(id);

        if(checkExist) {
            BarangEntity barangEntity = barangRepository.findById(id).orElseThrow();

            if(barangEntity.getIdUser().equals(user.getId())) {
                if(barangEntity.getKodeBarang().equals(request.getKodeBarang())) {
                    barangEntity.setKodeBarang(request.getKodeBarang());
                }
                barangEntity.setNamaBarang(request.getNamaBarang());
                barangEntity.setJumlahStok(request.getJumlahStok());
                barangEntity.setHargaSatuan(request.getHargaSatuan());
                barangRepository.save(barangEntity);

                return toBarangResponse(barangEntity);
            } else {
                return null;
            }

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
}
