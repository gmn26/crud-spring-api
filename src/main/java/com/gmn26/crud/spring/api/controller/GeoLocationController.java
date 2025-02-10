package com.gmn26.crud.spring.api.controller;

import com.gmn26.crud.spring.api.bean.ProvinsiResponse;
import com.gmn26.crud.spring.api.bean.WebResponse;
import com.gmn26.crud.spring.api.service.GeoLocationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/geolocation")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class GeoLocationController {

    private final GeoLocationService geoLocationService;

    @GetMapping(path = "/provinsi",produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<ProvinsiResponse>> fetchProvinsi() {
        List<ProvinsiResponse> provinsiResponses = geoLocationService.fetchProvinsi();

        return WebResponse.<List<ProvinsiResponse>>builder()
                .success(true)
                .message("Successfully retrieved provinsi")
                .data(provinsiResponses)
                .build();
    }

}
