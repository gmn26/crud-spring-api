package com.gmn26.crud.spring.api.repository;

import com.gmn26.crud.spring.api.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID>, QuerydslPredicateExecutor<TransactionEntity> {
}
