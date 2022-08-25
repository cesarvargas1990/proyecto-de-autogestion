package com.supergiros.portalautogestion.web.rest;

import com.supergiros.portalautogestion.domain.TransaccionesNomina;
import com.supergiros.portalautogestion.service.FacadeService;
import com.supergiros.portalautogestion.service.dto.TransaccionesMSDTO;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FacadeResource {

    @Autowired
    FacadeService facadeService;

    @GetMapping("/getTirilla")
    public ResponseEntity<ByteArrayResource> downloadFile(
        @RequestParam("pin") String pin,
        @RequestParam("numberDocument") String numberDocument
    ) throws Exception {
        try {
            ByteArrayResource tirilla = facadeService.getTirillaFacade(pin, numberDocument);
            return new ResponseEntity<ByteArrayResource>(tirilla, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
    }

    @GetMapping("/getRegistros")
    public ResponseEntity<List<TransaccionesNomina>> getRegistros(@RequestBody TransaccionesMSDTO solicitud)
        throws IOException, InterruptedException {
        List<TransaccionesNomina> registros = facadeService.getTransacciones(solicitud);
        return new ResponseEntity<List<TransaccionesNomina>>(registros, HttpStatus.OK);
    }
}
