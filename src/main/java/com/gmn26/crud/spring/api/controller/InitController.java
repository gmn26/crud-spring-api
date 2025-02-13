package com.gmn26.crud.spring.api.controller;


import com.gmn26.crud.spring.api.bean.WebResponse;
import com.gmn26.crud.spring.api.service.ImportCsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.Media;

@RestController
@RequestMapping("/api/v1/init")
@RequiredArgsConstructor
public class InitController {

    private final ImportCsvService importCsvService;

    @PostMapping(path = "/provinsi",produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse initProvinsi() {
        importCsvService.importProvinsi("prov.csv");

        return WebResponse.builder()
                .success(true)
                .message("Success init provinsi")
                .data(null)
                .build();
    }

    @PostMapping(path = "/kota", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse initKota() {
        importCsvService.importKota("city1.csv");

        return WebResponse.builder()
                .success(true)
                .message("Success init kota")
                .data(null)
                .build();
    }

    @PostMapping(path = "/kecamatan", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse initKecamatan() {
        importCsvService.importKecamatan("kec.csv");

        return WebResponse.builder()
                .success(true)
                .message("Success init kecamatan")
                .data(null)
                .build();
    }

    @PostMapping(path = "/kelurahan", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse initKelurahan() {
        importCsvService.importKelurahan("kel.csv");

        return WebResponse.builder()
                .success(true)
                .message("Success init kelurahan")
                .data(null)
                .build();
    }

}
