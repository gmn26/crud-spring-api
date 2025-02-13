package com.gmn26.crud.spring.api.service;

import com.gmn26.crud.spring.api.entity.KecamatanEntity;
import com.gmn26.crud.spring.api.entity.KelurahanEntity;
import com.gmn26.crud.spring.api.entity.KotaEntity;
import com.gmn26.crud.spring.api.entity.ProvinsiEntity;
import com.gmn26.crud.spring.api.repository.KecamatanRepository;
import com.gmn26.crud.spring.api.repository.KelurahanRepository;
import com.gmn26.crud.spring.api.repository.KotaRepository;
import com.gmn26.crud.spring.api.repository.ProvinsiRepository;
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
public class ImportCsvService {

    private final ProvinsiRepository provinsiRepository;

    private final KotaRepository kotaRepository;

    private final KecamatanRepository kecamatanRepository;

    private final KelurahanRepository kelurahanRepository;

    public void importProvinsi(String csvProvinsi) {
        try {
            ClassPathResource resource = new ClassPathResource(csvProvinsi);

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
                 CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

                for (CSVRecord record : csvParser) {

                    List<String> values = record.toList();

                    String[] datas = values.get(0).split(";");

                    if(provinsiRepository.existsById(Long.parseLong(datas[0]))) {
                        continue;
                    }

                    ProvinsiEntity provinsi = new ProvinsiEntity();

                    provinsi.setId(Long.parseLong(datas[0]));

                    provinsi.setNamaProvinsi(datas[1]);

                    provinsiRepository.save(provinsi);

                    log.info("Provinsi tersimpan : {}", provinsi.getNamaProvinsi());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Gagal mengimpor data dari CSV di resources: " + e.getMessage());
        }
    }

    public void importKota(String csvKota) {
        try {
            ClassPathResource resource = new ClassPathResource(csvKota);

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
                 CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

                for (CSVRecord record : csvParser) {

                    List<String> values = record.toList();

                    String[] datas = values.get(0).split(";");

                    if(kotaRepository.existsById(Long.parseLong(datas[0]))) {
                        continue;
                    }

                    KotaEntity kota = new KotaEntity();

                    kota.setId(Long.parseLong(datas[0]));

                    kota.setNamaKota(datas[1]);

                    if(provinsiRepository.existsById(Long.parseLong(datas[2]))){
                        ProvinsiEntity provinsi = provinsiRepository.findById(Long.parseLong(datas[2])).orElseThrow();

                        kota.setProvinsi(provinsi);

                        kotaRepository.save(kota);
                    } else {
                        continue;
                    }

                    log.info("Kota tersimpan : {}", kota.getNamaKota());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Gagal mengimpor data dari CSV di resources: " + e.getMessage());
        }
    }

    public void importKecamatan(String csvKecamatan) {
        try {
            ClassPathResource resource = new ClassPathResource(csvKecamatan);

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
                 CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {


                for (CSVRecord record : csvParser) {

                    List<String> values = record.toList();

                    String[] datas = values.get(0).split(";");

                    if(kecamatanRepository.existsById(Long.parseLong(datas[0]))) {
                        continue;
                    }

                    KecamatanEntity kecamatan = new KecamatanEntity();

                    kecamatan.setId(Long.parseLong(datas[0]));

                    kecamatan.setNamaKecamatan(datas[1]);

                    if(kotaRepository.existsById(Long.parseLong(datas[2]))){
                        KotaEntity kota =  kotaRepository.findById(Long.parseLong(datas[2])).orElseThrow();

                        kecamatan.setKota(kota);

                        kecamatanRepository.save(kecamatan);
                    } else {
                        continue;
                    }

                    log.info("Kecamatan tersimpan : {}", kecamatan.getNamaKecamatan());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Gagal mengimpor data dari CSV di resources: " + e.getMessage(), e);
        }
    }

    public void importKelurahan(String csvKelurahan) {
        try {
            ClassPathResource resource = new ClassPathResource(csvKelurahan);

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
                 CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

                for (CSVRecord record : csvParser ) {

                    List<String> values = record.toList();

                    String[] datas = values.get(0).split(";");

                    if(kelurahanRepository.existsById(Long.parseLong(datas[0]))) {
                        continue;
                    }

                    KelurahanEntity kelurahan = new KelurahanEntity();

                    kelurahan.setId(Long.parseLong(datas[0]));

                    kelurahan.setNamaKelurahan(datas[1]);

                    if(kecamatanRepository.existsById(Long.parseLong(datas[2]))){
                        KecamatanEntity kecamatan =  kecamatanRepository.findById(Long.parseLong(datas[2])).orElseThrow();

                        kelurahan.setKecamatan(kecamatan);

                        kelurahanRepository.save(kelurahan);
                    } else {
                        return;
                    }

                    log.info("Kelurahan tersimpan : {}", kelurahan.getNamaKelurahan());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Gagal mengimpor data dari CSV di resources: " + e.getMessage(), e);
        }
    }

}
