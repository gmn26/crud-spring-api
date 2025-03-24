package com.gmn26.crud.spring.api.repository;

import com.gmn26.crud.spring.api.entity.SidebarMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SidebarMenuRepository extends JpaRepository<SidebarMenuEntity, Long>, QuerydslPredicateExecutor<SidebarMenuEntity> {
    boolean existsByTitle(String title);
}
