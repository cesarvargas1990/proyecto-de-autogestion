package com.supergiros.portalautogestion.service;

import com.supergiros.portalautogestion.domain.TransaccionesNomina;
import com.supergiros.portalautogestion.service.dto.TransaccionesMSDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
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
        String url = tirillaUrl + "/download" + "?pin=" + pin + "&numberDocument=" + numberDocument;
        return this.restTemplate.getForObject(url, ByteArrayResource.class);
    }

    public List<TransaccionesNomina> getTransacciones(TransaccionesMSDTO solicitud) throws InterruptedException {
        String url = tirillaUrl + "/consulta/getByDocument";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<List<TransaccionesNomina>> rateResponse = restTemplate.exchange(
            url,
            HttpMethod.POST,
            new HttpEntity<>(solicitud),
            new ParameterizedTypeReference<List<TransaccionesNomina>>() {}
        );
        List<TransaccionesNomina> registros = rateResponse.getBody();
        return registros;
    }
}
