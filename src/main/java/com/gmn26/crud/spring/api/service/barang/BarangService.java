package com.gmn26.crud.spring.api.service.barang;

import com.gmn26.crud.spring.api.dto.barang.BarangResponse;
import com.gmn26.crud.spring.api.dto.barang.CreateBarangDto;

import java.util.List;

public interface BarangService {
    public List<BarangResponse> listAll();
    public BarangResponse create(CreateBarangDto request);
    public BarangResponse update(Long id, CreateBarangDto request);
    public BarangResponse delete(Long id);
}
