package com.gmn26.crud.spring.api.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProvinsiResponse {
    private Long id;
    private String namaProvinsi;
}
