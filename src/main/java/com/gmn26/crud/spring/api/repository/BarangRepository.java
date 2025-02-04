package com.gmn26.crud.spring.api.repository;

import com.gmn26.crud.spring.api.entity.BarangEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarangRepository extends JpaRepository<BarangEntity, Long> {
    List<BarangEntity> findAll();
    Boolean existsBarangByKodeBarang(String kodeBarang);
}
