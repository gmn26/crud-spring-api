package com.gmn26.crud.spring.api.service;

import com.gmn26.crud.spring.api.bean.geolocation.KecamatanResponse;
import com.gmn26.crud.spring.api.bean.geolocation.KelurahanResponse;
import com.gmn26.crud.spring.api.bean.geolocation.KotaResponse;
import com.gmn26.crud.spring.api.bean.geolocation.ProvinsiResponse;
import com.gmn26.crud.spring.api.entity.*;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeoLocationService {

    @PersistenceContext
    private EntityManager entityManager;

    private ProvinsiResponse toProvinsiResponse(ProvinsiEntity provinsiEntity) {
        return ProvinsiResponse.builder()
                .id(provinsiEntity.getId())
                .namaProvinsi(provinsiEntity.getNamaProvinsi())
                .build();
    }

    private KotaResponse toKotaResponse(KotaEntity kotaEntity) {
        return KotaResponse.builder()
                .id(kotaEntity.getId())
                .namaKota(kotaEntity.getNamaKota())
                .idProvinsi(kotaEntity.getProvinsi().getId())
                .build();
    }

    private KecamatanResponse toKecamatanResponse(KecamatanEntity kecamatanEntity) {
        return KecamatanResponse.builder()
                .id(kecamatanEntity.getId())
                .namaKecamatan(kecamatanEntity.getNamaKecamatan())
                .idKota(kecamatanEntity.getKota().getId())
                .build();
    }

    private KelurahanResponse toKelurahanResponse(KelurahanEntity kelurahanEntity) {
        return KelurahanResponse.builder()
                .id(kelurahanEntity.getId())
                .namaKelurahan(kelurahanEntity.getNamaKelurahan())
                .idKecamatan(kelurahanEntity.getKecamatan().getId())
                .build();
    }

    public List<ProvinsiResponse> fetchProvinsi() {
        QProvinsiEntity qProvinsiEntity = QProvinsiEntity.provinsiEntity;

        JPAQuery<ProvinsiEntity> query = new JPAQuery<>(entityManager);

        List<ProvinsiEntity> provinsiEntities = query
                .from(qProvinsiEntity)
                .select(qProvinsiEntity)
                .fetch();

        return provinsiEntities.stream().map(this::toProvinsiResponse).collect(Collectors.toList());
    }

    public List<KotaResponse> fetchKota(Long id) {
        QKotaEntity qKotaEntity = QKotaEntity.kotaEntity;

        JPAQuery<KotaEntity> query = new JPAQuery<>(entityManager);

        List<KotaEntity> kotaEntity = query
                .from(qKotaEntity)
                .where(qKotaEntity.provinsi.id.eq(id))
                .select(qKotaEntity)
                .fetch();

        return kotaEntity.stream().map(this::toKotaResponse).collect(Collectors.toList());
    }

    public List<KecamatanResponse> fetchKecamatan(Long id) {
        QKecamatanEntity qKecamatanEntity = QKecamatanEntity.kecamatanEntity;

        JPAQuery<KecamatanEntity> query = new JPAQuery<>(entityManager);

        List<KecamatanEntity> kecamatanEntities = query
                .from(qKecamatanEntity)
                .where(qKecamatanEntity.kota.id.eq(id))
                .select(qKecamatanEntity)
                .fetch();

        return kecamatanEntities.stream().map(this::toKecamatanResponse).collect(Collectors.toList());
    }

    public List<KelurahanResponse> fetchKelurahan(Long id) {
        QKelurahanEntity qKelurahanEntity = QKelurahanEntity.kelurahanEntity;

        JPAQuery<KelurahanEntity> query = new JPAQuery<>(entityManager);

        List<KelurahanEntity> kelurahanEntities = query
                .from(qKelurahanEntity)
                .where(qKelurahanEntity.kecamatan.id.eq(id))
                .select(qKelurahanEntity)
                .fetch();

        return kelurahanEntities.stream().map(this::toKelurahanResponse).collect(Collectors.toList());
    }

}
