package com.gmn26.crud.spring.api.service;

import com.gmn26.crud.spring.api.entity.Barang;
import com.gmn26.crud.spring.api.repository.BarangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarangService {
    private final BarangRepository barangRepository;

    @Autowired
    public BarangService(BarangRepository barangRepository) {
        this.barangRepository = barangRepository;
    }

    public List<Barang> getAll() {
        return barangRepository.findAll();
    }
}
