package com.gmn26.crud.spring.api.service;

import com.gmn26.crud.spring.api.bean.barang.BarangResponse;
import com.gmn26.crud.spring.api.bean.barang.CreateBarangDto;
import com.gmn26.crud.spring.api.entity.BarangEntity;
import com.gmn26.crud.spring.api.entity.QBarangEntity;
import com.gmn26.crud.spring.api.entity.UserEntity;
import com.gmn26.crud.spring.api.repository.BarangRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.querydsl.jpa.impl.JPAQuery;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BarangService {

    private final BarangRepository barangRepository;

    @PersistenceContext
    private EntityManager entityManager;

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

    public List<BarangResponse> findAllBarang(
            String kodeBarang
    ) {
        QBarangEntity qBarang = QBarangEntity.barangEntity;

        JPAQuery<BarangEntity> query = new JPAQuery<>(entityManager);

        query.from(qBarang);

        if(kodeBarang != null) {
            query.where(qBarang.kodeBarang.contains(kodeBarang));
        }

        List<BarangEntity> barangEntities = query.fetch();

        return barangEntities.stream().map(this::toBarangResponse).collect(Collectors.toList());
    }

    public List<BarangResponse> fetchAuthedBarang(UserEntity user) {
        QBarangEntity qBarang = QBarangEntity.barangEntity;

        JPAQuery<BarangEntity> query = new JPAQuery<>(entityManager);

        UUID id = user.getId();

        query.from(qBarang);

        query.where(qBarang.idUser.eq(id));

        List<BarangEntity> barangEntities = query.fetch();

        return barangEntities.stream().map(this::toBarangResponse).collect(Collectors.toList());
    }

    public BarangResponse create(UserEntity user, CreateBarangDto request) {
        Boolean checkDuplicate = barangRepository.existsBarangByKodeBarang(request.getKodeBarang());

        if (checkDuplicate) {
            return null;
        }

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

    public BarangResponse update(Long id, UserEntity user, CreateBarangDto request) {
        boolean checkExist = barangRepository.existsById(id);

        if (checkExist) {
            BarangEntity barangEntity = barangRepository.findById(id).orElseThrow();

            if (barangEntity.getIdUser().equals(user.getId())) {
                barangEntity.setKodeBarang(request.getKodeBarang());
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
             barangRepository.delete(barangEntity);
             return toBarangResponse(barangEntity);
        }
        return null;
    }
}
