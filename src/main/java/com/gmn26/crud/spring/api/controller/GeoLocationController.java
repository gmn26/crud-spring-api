package com.gmn26.crud.spring.api.controller;

import com.gmn26.crud.spring.api.bean.geolocation.KecamatanResponse;
import com.gmn26.crud.spring.api.bean.geolocation.KelurahanResponse;
import com.gmn26.crud.spring.api.bean.geolocation.KotaResponse;
import com.gmn26.crud.spring.api.bean.geolocation.ProvinsiResponse;
import com.gmn26.crud.spring.api.bean.WebResponse;
import com.gmn26.crud.spring.api.service.GeoLocationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/geolocation")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class GeoLocationController {

    private final GeoLocationService geoLocationService;

    @GetMapping(path = "/provinsi", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<ProvinsiResponse>> fetchProvinsi() {
        List<ProvinsiResponse> provinsiResponses = geoLocationService.fetchProvinsi();

        return WebResponse.<List<ProvinsiResponse>>builder()
                .success(true)
                .message("Successfully retrieved provinsi")
                .data(provinsiResponses)
                .build();
    }

    @GetMapping(path = "/kota", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<KotaResponse>> fetchKota(@RequestParam Long id) {
        List<KotaResponse> kotaResponses = geoLocationService.fetchKota(id);

        return WebResponse.<List<KotaResponse>>builder()
                .success(true)
                .message("Succesfully retrieved kota")
                .data(kotaResponses)
                .build();
    }

    @GetMapping(path = "/kecamatan", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<KecamatanResponse>> fetchKecamatan(@RequestParam Long id) {
        List<KecamatanResponse> kecamatanResponses = geoLocationService.fetchKecamatan(id);

        return WebResponse.<List<KecamatanResponse>>builder()
                .success(true)
                .message("Successfully retrieved kecamatan")
                .data(kecamatanResponses)
                .build();
    }

    @GetMapping(path = "/kelurahan", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<KelurahanResponse>> fetchKelurahan(@RequestParam Long id) {
        List<KelurahanResponse> kelurahanResponses = geoLocationService.fetchKelurahan(id);

        return WebResponse.<List<KelurahanResponse>>builder()
                .success(true)
                .message("Successfully retrieved kelurahan")
                .data(kelurahanResponses)
                .build();
    }
}
