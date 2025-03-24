package com.gmn26.crud.spring.api.service;

import com.gmn26.crud.spring.api.entity.ProvinsiEntity;
import com.gmn26.crud.spring.api.entity.SidebarMenuEntity;
import com.gmn26.crud.spring.api.repository.SidebarMenuRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class InitSidebarMenuService {

    private final SidebarMenuRepository sidebarMenuRepository;

    public void initSidebarMenu() {
        try {
            ClassPathResource resource = new ClassPathResource("initSidebarMenu.csv");

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
                 CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

                for (CSVRecord record : csvParser) {

                    List<String> values = record.toList();

                    String[] datas = values.get(0).split(";");

                    if(sidebarMenuRepository.existsByTitle(datas[0])) {
                        continue;
                    }

                    SidebarMenuEntity sidebarMenuEntity = new SidebarMenuEntity();

                    sidebarMenuEntity.setTitle(datas[1]);

                    sidebarMenuEntity.setRoute(datas[2]);

                    sidebarMenuEntity.setGrantedAccess(datas[3]);

                    sidebarMenuRepository.save(sidebarMenuEntity);

                    log.info("Menu tersimpan : {}", sidebarMenuEntity.getRoute());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Gagal mengimpor data dari CSV di resources: " + e.getMessage());
        }
    }

}
