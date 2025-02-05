package com.gmn26.crud.spring.api.service.barang;

import com.gmn26.crud.spring.api.dto.barang.BarangResponse;
import com.gmn26.crud.spring.api.dto.barang.CreateBarangDto;
import com.gmn26.crud.spring.api.entity.UserEntity;

import java.util.List;

public interface BarangService {
    public List<BarangResponse> listAll();
    public List<BarangResponse> listAuthedBarang(UserEntity user);
    public BarangResponse create(UserEntity user, CreateBarangDto request);
    public BarangResponse update(Long id, UserEntity user, CreateBarangDto request);
    public BarangResponse delete(Long id);
}
