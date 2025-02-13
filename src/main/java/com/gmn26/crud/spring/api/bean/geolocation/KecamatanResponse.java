package com.gmn26.crud.spring.api.bean.geolocation;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KecamatanResponse {
    private Long id;
    private String namaKecamatan;
    private Long idKota;
}
