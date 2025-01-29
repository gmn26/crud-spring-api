package com.gmn26.crud.spring.api.controller;

import com.gmn26.crud.spring.api.entity.Barang;
import com.gmn26.crud.spring.api.service.BarangService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/barangs")
public class BarangController {
    private final BarangService barangService;

    public BarangController(BarangService barangService) {
        this.barangService = barangService;
    }

    @GetMapping
    public List<Barang> getBarangs() {
        return barangService.getAll();
    }
}
