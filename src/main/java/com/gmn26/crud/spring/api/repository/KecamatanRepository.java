package com.gmn26.crud.spring.api.repository;

import com.gmn26.crud.spring.api.entity.KecamatanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface KecamatanRepository extends JpaRepository<KecamatanEntity, Long>, QuerydslPredicateExecutor<KecamatanEntity> {
    boolean existsByNamaKecamatan(String namaKecamatan);
}
