package com.gmn26.crud.spring.api.repository;

import com.gmn26.crud.spring.api.entity.ProvinsiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProvinsiRepository extends JpaRepository<ProvinsiEntity, Long>, QuerydslPredicateExecutor<ProvinsiEntity> {

}
