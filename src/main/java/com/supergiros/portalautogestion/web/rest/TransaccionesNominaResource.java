package com.supergiros.portalautogestion.web.rest;

import com.supergiros.portalautogestion.domain.TransaccionesNomina;
import com.supergiros.portalautogestion.repository.TransaccionesNominaRepository;
import com.supergiros.portalautogestion.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.supergiros.portalautogestion.domain.TransaccionesNomina}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TransaccionesNominaResource {

    private final Logger log = LoggerFactory.getLogger(TransaccionesNominaResource.class);

    private static final String ENTITY_NAME = "transaccionesNomina";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransaccionesNominaRepository transaccionesNominaRepository;

    public TransaccionesNominaResource(TransaccionesNominaRepository transaccionesNominaRepository) {
        this.transaccionesNominaRepository = transaccionesNominaRepository;
    }

    /**
     * {@code POST  /transacciones-nominas} : Create a new transaccionesNomina.
     *
     * @param transaccionesNomina the transaccionesNomina to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transaccionesNomina, or with status {@code 400 (Bad Request)} if the transaccionesNomina has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transacciones-nominas")
    public ResponseEntity<TransaccionesNomina> createTransaccionesNomina(@RequestBody TransaccionesNomina transaccionesNomina)
        throws URISyntaxException {
        log.debug("REST request to save TransaccionesNomina : {}", transaccionesNomina);
        if (transaccionesNomina.getId() != null) {
            throw new BadRequestAlertException("A new transaccionesNomina cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransaccionesNomina result = transaccionesNominaRepository.save(transaccionesNomina);
        return ResponseEntity
            .created(new URI("/api/transacciones-nominas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transacciones-nominas/:id} : Updates an existing transaccionesNomina.
     *
     * @param id the id of the transaccionesNomina to save.
     * @param transaccionesNomina the transaccionesNomina to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transaccionesNomina,
     * or with status {@code 400 (Bad Request)} if the transaccionesNomina is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transaccionesNomina couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transacciones-nominas/{id}")
    public ResponseEntity<TransaccionesNomina> updateTransaccionesNomina(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TransaccionesNomina transaccionesNomina
    ) throws URISyntaxException {
        log.debug("REST request to update TransaccionesNomina : {}, {}", id, transaccionesNomina);
        if (transaccionesNomina.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transaccionesNomina.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transaccionesNominaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TransaccionesNomina result = transaccionesNominaRepository.save(transaccionesNomina);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transaccionesNomina.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /transacciones-nominas/:id} : Partial updates given fields of an existing transaccionesNomina, field will ignore if it is null
     *
     * @param id the id of the transaccionesNomina to save.
     * @param transaccionesNomina the transaccionesNomina to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transaccionesNomina,
     * or with status {@code 400 (Bad Request)} if the transaccionesNomina is not valid,
     * or with status {@code 404 (Not Found)} if the transaccionesNomina is not found,
     * or with status {@code 500 (Internal Server Error)} if the transaccionesNomina couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/transacciones-nominas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TransaccionesNomina> partialUpdateTransaccionesNomina(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TransaccionesNomina transaccionesNomina
    ) throws URISyntaxException {
        log.debug("REST request to partial update TransaccionesNomina partially : {}, {}", id, transaccionesNomina);
        if (transaccionesNomina.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transaccionesNomina.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transaccionesNominaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TransaccionesNomina> result = transaccionesNominaRepository
            .findById(transaccionesNomina.getId())
            .map(existingTransaccionesNomina -> {
                if (transaccionesNomina.getTipoDocumentoBenef() != null) {
                    existingTransaccionesNomina.setTipoDocumentoBenef(transaccionesNomina.getTipoDocumentoBenef());
                }
                if (transaccionesNomina.getNumeroDocumentoBenef() != null) {
                    existingTransaccionesNomina.setNumeroDocumentoBenef(transaccionesNomina.getNumeroDocumentoBenef());
                }
                if (transaccionesNomina.getNombreUno() != null) {
                    existingTransaccionesNomina.setNombreUno(transaccionesNomina.getNombreUno());
                }
                if (transaccionesNomina.getNombreDos() != null) {
                    existingTransaccionesNomina.setNombreDos(transaccionesNomina.getNombreDos());
                }
                if (transaccionesNomina.getApellidoUno() != null) {
                    existingTransaccionesNomina.setApellidoUno(transaccionesNomina.getApellidoUno());
                }
                if (transaccionesNomina.getApellidoDos() != null) {
                    existingTransaccionesNomina.setApellidoDos(transaccionesNomina.getApellidoDos());
                }
                if (transaccionesNomina.getNombreUnoPago() != null) {
                    existingTransaccionesNomina.setNombreUnoPago(transaccionesNomina.getNombreUnoPago());
                }
                if (transaccionesNomina.getNombreDosPago() != null) {
                    existingTransaccionesNomina.setNombreDosPago(transaccionesNomina.getNombreDosPago());
                }
                if (transaccionesNomina.getApellidoUnoPago() != null) {
                    existingTransaccionesNomina.setApellidoUnoPago(transaccionesNomina.getApellidoUnoPago());
                }
                if (transaccionesNomina.getApellidoDosPago() != null) {
                    existingTransaccionesNomina.setApellidoDosPago(transaccionesNomina.getApellidoDosPago());
                }
                if (transaccionesNomina.getFechaPago() != null) {
                    existingTransaccionesNomina.setFechaPago(transaccionesNomina.getFechaPago());
                }
                if (transaccionesNomina.getHoraPago() != null) {
                    existingTransaccionesNomina.setHoraPago(transaccionesNomina.getHoraPago());
                }
                if (transaccionesNomina.getPinPago() != null) {
                    existingTransaccionesNomina.setPinPago(transaccionesNomina.getPinPago());
                }
                if (transaccionesNomina.getfKDepartamentoDePago() != null) {
                    existingTransaccionesNomina.setfKDepartamentoDePago(transaccionesNomina.getfKDepartamentoDePago());
                }
                if (transaccionesNomina.getfKMunicipioDePago() != null) {
                    existingTransaccionesNomina.setfKMunicipioDePago(transaccionesNomina.getfKMunicipioDePago());
                }
                if (transaccionesNomina.getfKDepartamento() != null) {
                    existingTransaccionesNomina.setfKDepartamento(transaccionesNomina.getfKDepartamento());
                }
                if (transaccionesNomina.getfKMunicipio() != null) {
                    existingTransaccionesNomina.setfKMunicipio(transaccionesNomina.getfKMunicipio());
                }
                if (transaccionesNomina.getfKIdConvenio() != null) {
                    existingTransaccionesNomina.setfKIdConvenio(transaccionesNomina.getfKIdConvenio());
                }
                if (transaccionesNomina.getfKIdPrograma() != null) {
                    existingTransaccionesNomina.setfKIdPrograma(transaccionesNomina.getfKIdPrograma());
                }
                if (transaccionesNomina.getFechaDePago() != null) {
                    existingTransaccionesNomina.setFechaDePago(transaccionesNomina.getFechaDePago());
                }
                if (transaccionesNomina.getValorGiro() != null) {
                    existingTransaccionesNomina.setValorGiro(transaccionesNomina.getValorGiro());
                }
                if (transaccionesNomina.getEstado() != null) {
                    existingTransaccionesNomina.setEstado(transaccionesNomina.getEstado());
                }
                if (transaccionesNomina.getPeriodoPago() != null) {
                    existingTransaccionesNomina.setPeriodoPago(transaccionesNomina.getPeriodoPago());
                }
                if (transaccionesNomina.getMotivoAnulacion() != null) {
                    existingTransaccionesNomina.setMotivoAnulacion(transaccionesNomina.getMotivoAnulacion());
                }
                if (transaccionesNomina.getFechaVigencia() != null) {
                    existingTransaccionesNomina.setFechaVigencia(transaccionesNomina.getFechaVigencia());
                }
                if (transaccionesNomina.getFechaCargue() != null) {
                    existingTransaccionesNomina.setFechaCargue(transaccionesNomina.getFechaCargue());
                }
                if (transaccionesNomina.getNota() != null) {
                    existingTransaccionesNomina.setNota(transaccionesNomina.getNota());
                }
                if (transaccionesNomina.getRedPagadora() != null) {
                    existingTransaccionesNomina.setRedPagadora(transaccionesNomina.getRedPagadora());
                }
                if (transaccionesNomina.getObservacionControl() != null) {
                    existingTransaccionesNomina.setObservacionControl(transaccionesNomina.getObservacionControl());
                }
                if (transaccionesNomina.getSolicitudAutorizacion() != null) {
                    existingTransaccionesNomina.setSolicitudAutorizacion(transaccionesNomina.getSolicitudAutorizacion());
                }

                return existingTransaccionesNomina;
            })
            .map(transaccionesNominaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transaccionesNomina.getId().toString())
        );
    }

    /**
     * {@code GET  /transacciones-nominas} : get all the transaccionesNominas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transaccionesNominas in body.
     */
    @GetMapping("/transacciones-nominas")
    public List<TransaccionesNomina> getAllTransaccionesNominas() {
        log.debug("REST request to get all TransaccionesNominas");
        return transaccionesNominaRepository.findAll();
    }

    /**
     * {@code GET  /transacciones-nominas/:id} : get the "id" transaccionesNomina.
     *
     * @param id the id of the transaccionesNomina to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transaccionesNomina, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transacciones-nominas/{id}")
    public ResponseEntity<TransaccionesNomina> getTransaccionesNomina(@PathVariable Long id) {
        log.debug("REST request to get TransaccionesNomina : {}", id);
        Optional<TransaccionesNomina> transaccionesNomina = transaccionesNominaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(transaccionesNomina);
    }

    /**
     * {@code DELETE  /transacciones-nominas/:id} : delete the "id" transaccionesNomina.
     *
     * @param id the id of the transaccionesNomina to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transacciones-nominas/{id}")
    public ResponseEntity<Void> deleteTransaccionesNomina(@PathVariable Long id) {
        log.debug("REST request to delete TransaccionesNomina : {}", id);
        transaccionesNominaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
