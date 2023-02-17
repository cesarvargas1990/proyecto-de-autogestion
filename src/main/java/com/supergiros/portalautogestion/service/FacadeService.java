package com.supergiros.portalautogestion.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supergiros.portalautogestion.domain.TransaccionesNomina;
import com.supergiros.portalautogestion.service.dto.TransaccionesMSDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
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

    public List<TransaccionesNomina> getTransacciones(TransaccionesMSDTO solicitud) throws InterruptedException, JsonProcessingException {
        String url = tirillaUrl + "/consulta/getByDocument";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        TypeReference<List<TransaccionesNomina>> typeReference = new TypeReference<List<TransaccionesNomina>>() {};
        ResponseEntity<String> response = restTemplate.exchange(
            url,
            HttpMethod.POST,
            new HttpEntity<>(solicitud),
            String.class
        );
        String jsonResponse = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> transaccionesMap = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });
        List<TransaccionesNomina> transacciones = getTransaccionesNominasRest(transaccionesMap);
        return transacciones;
    }

    @NotNull
    private static List<TransaccionesNomina> getTransaccionesNominasRest(List<Map<String, Object>> transaccionesMap) {
        List<TransaccionesNomina> transacciones = new ArrayList<>();
        for (Map<String, Object> transaccionMap : transaccionesMap) {
            TransaccionesNomina transaccion = new TransaccionesNomina();
            transaccion.setValorGiro(transaccionMap.get("valorGiro") != null ? transaccionMap.get("valorGiro").toString() : null);
            transaccion.setPinPago(transaccionMap.get("pinPago") != null ? transaccionMap.get("pinPago").toString() : null);
            transaccion.setfKDepartamentoDePago(transaccionMap.get("fKDepartamentoDePago") != null ? transaccionMap.get("fKDepartamentoDePago").toString() : null);
            transaccion.setfKMunicipioDePago(transaccionMap.get("fKMunicipioDePago") != null ? transaccionMap.get("fKMunicipioDePago").toString() : null);
            transaccion.setAgenciaOficinaNombre(transaccionMap.get("agenciaOficinaNombre") != null ? transaccionMap.get("agenciaOficinaNombre").toString() : null);
            transaccion.setMotivoAnulacion(transaccionMap.get("motivoAnulacion") != null ? transaccionMap.get("motivoAnulacion").toString() : null);
            transaccion.setEstado(transaccionMap.get("estado") != null ? transaccionMap.get("estado").toString() : null);
            transaccion.setTipoDocumentoBenef(transaccionMap.get("tipoDocumentoBenef") != null ? transaccionMap.get("tipoDocumentoBenef").toString() : null);
            transaccion.setNumeroDocumentoBenef(Long.valueOf(transaccionMap.get("numeroDocumentoBenef").toString()));
            transaccion.setfKIdConvenio(transaccionMap.get("fKIdConvenio") != null ? transaccionMap.get("fKIdConvenio").toString() : null);
            transaccion.setfKIdPrograma(transaccionMap.get("fKIdPrograma") != null ? transaccionMap.get("fKIdPrograma").toString() : null);
            transaccion.setHoraPago(transaccionMap.get("horaPago") != null ? transaccionMap.get("horaPago").toString() : null);
            transaccion.setFechaPago(transaccionMap.get("fechaPago") != null ? LocalDate.parse(transaccionMap.get("fechaPago").toString()) : null);
            transaccion.setObservacionControl(transaccionMap.get("observacionControl") != null ? transaccionMap.get("observacionControl").toString() : null);
            transaccion.setNombreUnoPago(transaccionMap.get("nombreUnoPago") != null ? transaccionMap.get("nombreUnoPago").toString() : null);
            transaccion.setNombreDosPago(transaccionMap.get("nombreDosPago") != null ? transaccionMap.get("nombreDosPago").toString() : null);
            transaccion.setApellidoUnoPago(transaccionMap.get("apellidoUnoPago") != null ? transaccionMap.get("apellidoUnoPago").toString() : null);
            transaccion.setApellidoDosPago(transaccionMap.get("apellidoDosPago") != null ? transaccionMap.get("apellidoDosPago").toString() : null);
            transacciones.add(transaccion);
        }
        return transacciones;
    }
}
