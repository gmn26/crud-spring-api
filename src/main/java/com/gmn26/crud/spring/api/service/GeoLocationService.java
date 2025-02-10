package com.gmn26.crud.spring.api.service;

import com.gmn26.crud.spring.api.bean.ProvinsiResponse;
import com.gmn26.crud.spring.api.entity.ProvinsiEntity;
import com.gmn26.crud.spring.api.entity.QProvinsiEntity;
import com.gmn26.crud.spring.api.repository.ProvinsiRepository;
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

    public List<ProvinsiResponse> fetchProvinsi() {
        QProvinsiEntity qProvinsiEntity = QProvinsiEntity.provinsiEntity;

        JPAQuery<ProvinsiEntity> query = new JPAQuery<>(entityManager);

        query.select(qProvinsiEntity);

        List<ProvinsiEntity> provinsiEntities = query.fetch();

        return provinsiEntities.stream().map(this::toProvinsiResponse).collect(Collectors.toList());
    }

}
