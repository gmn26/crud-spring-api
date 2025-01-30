package com.gmn26.crud.spring.api.controller;

import com.gmn26.crud.spring.api.dto.BarangResponse;
import com.gmn26.crud.spring.api.dto.CreateBarangDto;
import com.gmn26.crud.spring.api.dto.WebResponse;
import com.gmn26.crud.spring.api.service.BarangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/barangs")
public class BarangController {
    @Autowired
    private BarangService barangService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<BarangResponse>> getAllBarangs() {
        List<BarangResponse> barangResponseList = barangService.listAll();
        return WebResponse.<List<BarangResponse>>builder()
                .success(true)
                .message("Success fetching barang")
                .data(barangResponseList)
                .build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<BarangResponse> createBarang(@RequestBody CreateBarangDto barangRequest) {
        BarangResponse barangResponse = barangService.create(barangRequest);
        return WebResponse.<BarangResponse>builder()
                .success(true)
                .message("Success creating barang")
                .data(barangResponse)
                .build();
    }
}
