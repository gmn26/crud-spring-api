package com.gmn26.crud.spring.api.controller;

import com.gmn26.crud.spring.api.bean.WebResponse;
import com.gmn26.crud.spring.api.bean.barang.BarangResponse;
import com.gmn26.crud.spring.api.bean.barang.CreateBarangDto;
import com.gmn26.crud.spring.api.entity.UserEntity;
import com.gmn26.crud.spring.api.service.BarangService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/barangs")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class BarangController {

    private final BarangService barangService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<BarangResponse>> fetchAllBarang(
            @RequestParam(required = false) String kodeBarang
    ) {
        List<BarangResponse> barangResponse = barangService.findAllBarang(kodeBarang);

        return WebResponse.<List<BarangResponse>>builder()
                .success(true)
                .message("Success fething all barang")
                .data(barangResponse)
                .build();
    }

    @GetMapping(path = "/owned", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<BarangResponse>> fetchAutedBarang() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal();
        List<BarangResponse> barangResponse = barangService.fetchAuthedBarang(user);

        return WebResponse.<List<BarangResponse>>builder()
                .success(true)
                .message("Success fething authed user barang")
                .data(barangResponse)
                .build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<BarangResponse> createBarang(@RequestBody CreateBarangDto request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal();
        BarangResponse barangResponse = barangService.create(user, request);

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
    public ResponseEntity<WebResponse<BarangResponse>> updateBarang(@PathVariable Long id, @RequestBody CreateBarangDto request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal();

        BarangResponse barangResponse = barangService.update(id, user, request);

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
                    .message("You dont have access to the barang")
                    .data(null)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WebResponse<BarangResponse>> deleteBarang(@PathVariable Long id) {
        BarangResponse barangResponse = barangService.delete(id);

        if (barangResponse != null) {
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
