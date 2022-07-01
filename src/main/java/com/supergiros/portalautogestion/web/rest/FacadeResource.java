package com.supergiros.portalautogestion.web.rest;

import com.supergiros.portalautogestion.service.FacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
