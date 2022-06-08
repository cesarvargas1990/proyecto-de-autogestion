package com.supergiros.portalautogestion.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FacadeService {

    @Value("${spring.application.tirillaURL}")
    private String tirillaUrl;

    private final RestTemplate restTemplate;

    public FacadeService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ByteArrayResource getTirillaFacade(String pin, String numberDocument) throws Exception {
        String url = tirillaUrl + "?pin=" + pin + "&numberDocument=" + numberDocument;
        return this.restTemplate.getForObject(url, ByteArrayResource.class);
    }
}
