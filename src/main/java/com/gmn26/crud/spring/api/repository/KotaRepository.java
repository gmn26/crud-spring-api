package com.gmn26.crud.spring.api.repository;

import com.gmn26.crud.spring.api.entity.KotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface KotaRepository extends JpaRepository<KotaEntity, Long>, QuerydslPredicateExecutor<KotaEntity> {
    boolean existsByNamaKota(String namaKota);
}
