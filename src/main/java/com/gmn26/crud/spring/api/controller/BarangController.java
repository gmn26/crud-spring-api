package com.gmn26.crud.spring.api.controller;

import com.gmn26.crud.spring.api.dto.barang.BarangResponse;
import com.gmn26.crud.spring.api.dto.barang.CreateBarangDto;
import com.gmn26.crud.spring.api.dto.WebResponse;
import com.gmn26.crud.spring.api.service.barang.BarangServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/barangs")
public class BarangController {
    @Autowired
    private BarangServiceImpl barangServiceImpl;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<BarangResponse>> getAllBarangs() {
        List<BarangResponse> barangResponseList = barangServiceImpl.listAll();

        return WebResponse.<List<BarangResponse>>builder()
                .success(true)
                .message("Success fetching barang")
                .data(barangResponseList)
                .build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<BarangResponse> createBarang(@RequestBody CreateBarangDto barangRequest) {
        BarangResponse barangResponse = barangServiceImpl.create(barangRequest);

        if (barangResponse != null) {
            return WebResponse.<BarangResponse>builder()
                    .success(true)
                    .message("Success creating barang")
                    .data(barangResponse)
                    .build();
        } else {
            return WebResponse.<BarangResponse>builder()
                    .success(true)
                    .message("Duplicated kode barang")
                    .data(null)
                    .build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<WebResponse<BarangResponse>> updateBarang(@PathVariable Long id, @RequestBody CreateBarangDto barangRequest) {
        BarangResponse barangResponse = barangServiceImpl.update(id, barangRequest);

        if (barangResponse != null) {
            WebResponse<BarangResponse> response = WebResponse.<BarangResponse>builder()
                    .success(true)
                    .message("Barang updated")
                    .data(barangResponse)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            WebResponse<BarangResponse> response = WebResponse.<BarangResponse>builder()
                    .success(true)
                    .message("Barang not found")
                    .data(null)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WebResponse<BarangResponse>> deleteBarang(@PathVariable Long id) {
        BarangResponse barangResponse = barangServiceImpl.delete(id);

        if(barangResponse != null) {
            WebResponse<BarangResponse> response = WebResponse.<BarangResponse>builder()
                    .success(true)
                    .message("Barang deleted")
                    .data(barangResponse)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            WebResponse<BarangResponse> response = WebResponse.<BarangResponse>builder()
                    .success(true)
                    .message("Barang not found")
                    .data(null)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
