package com.gmn26.crud.spring.api.repository;

import com.gmn26.crud.spring.api.entity.Barang;
//import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarangRepository extends JpaRepository<Barang, Long>, QuerydslPredicateExecutor<Barang> {
    List<Barang> findAll();
}
