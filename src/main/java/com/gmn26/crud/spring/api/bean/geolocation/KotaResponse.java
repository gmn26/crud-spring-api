package com.gmn26.crud.spring.api.bean.geolocation;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KotaResponse {
    private Long id;
    private String namaKota;
    private Long idProvinsi;
}
