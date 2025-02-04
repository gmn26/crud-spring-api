package com.gmn26.crud.spring.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmn26.crud.spring.api.dto.barang.BarangResponse;
import com.gmn26.crud.spring.api.dto.barang.CreateBarangDto;
import com.gmn26.crud.spring.api.dto.WebResponse;
import com.gmn26.crud.spring.api.entity.Barang;
import com.gmn26.crud.spring.api.repository.BarangRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
class BarangControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BarangRepository barangRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        barangRepository.deleteAll();
    }

    @Test
    void createBarangSuccess() throws Exception {
        CreateBarangDto createBarangDto = new CreateBarangDto();
        createBarangDto.setKodeBarang("Kode Barang");
        createBarangDto.setNamaBarang("Nama Barang");
        createBarangDto.setJumlahStok(10);
        createBarangDto.setHargaSatuan(BigDecimal.valueOf(1000.0));

        mockMvc.perform(
                post("/api/v1/barangs")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBarangDto))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<BarangResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                    new  TypeReference<>(){
                    });
            assertNull(response.getError());
            assertTrue(response.isSuccess());
            assertEquals("Kode Barang", response.getData().getKodeBarang());
            assertEquals("Nama Barang", response.getData().getNamaBarang());
            assertEquals(10, response.getData().getJumlahStok());
            assertEquals(BigDecimal.valueOf(1000.0), response.getData().getHargaSatuan());
            assertNotNull(response.getData().getTanggalMasuk());

            assertTrue(barangRepository.existsById(response.getData().getId()));
        });
    }

    @Test
    void updateBarangSuccess() throws Exception {
        Barang barang = new Barang();
        barang.setKodeBarang("Kode Barang");
        barang.setNamaBarang("Nama Barang");
        barang.setJumlahStok(100);
        barang.setHargaSatuan(BigDecimal.valueOf(1000.0));
        barang.setTanggalMasuk(LocalDateTime.now());

        barangRepository.save(barang);

        CreateBarangDto createBarangDto = new CreateBarangDto();
        createBarangDto.setKodeBarang("Kode Barang Edit");
        createBarangDto.setNamaBarang("Nama Barang Edit");
        createBarangDto.setJumlahStok(100);
        createBarangDto.setHargaSatuan(BigDecimal.valueOf(1000.0));

        mockMvc.perform(
                put("/api/v1/barangs/" + barang.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBarangDto))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<BarangResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                    new  TypeReference<>(){
                    });
            assertNull(response.getError());
            assertTrue(response.isSuccess());
            assertEquals("Kode Barang Edit", response.getData().getKodeBarang());
            assertEquals("Nama Barang Edit", response.getData().getNamaBarang());
            assertEquals(100, response.getData().getJumlahStok());
            assertEquals(BigDecimal.valueOf(1000.0), response.getData().getHargaSatuan());
            assertNotNull(response.getData().getTanggalMasuk());

            assertTrue(barangRepository.existsById(response.getData().getId()));
        });
    }
}