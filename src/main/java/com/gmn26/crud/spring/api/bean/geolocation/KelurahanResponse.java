package com.gmn26.crud.spring.api.bean.geolocation;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KelurahanResponse {
    private Long id;
    private String namaKelurahan;
    private Long idKecamatan;
}
