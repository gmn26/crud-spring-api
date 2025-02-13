package com.gmn26.crud.spring.api.repository;

import com.gmn26.crud.spring.api.entity.KelurahanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface KelurahanRepository extends JpaRepository<KelurahanEntity, Long>, QuerydslPredicateExecutor<KelurahanEntity> {

    boolean existsByNamaKelurahan(String namaKelurahan);
}
