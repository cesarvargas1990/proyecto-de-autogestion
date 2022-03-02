package com.supergiros.portalautogestion.web.rest;

import com.supergiros.portalautogestion.domain.Programas;
import com.supergiros.portalautogestion.repository.ProgramasRepository;
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
 * REST controller for managing {@link com.supergiros.portalautogestion.domain.Programas}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProgramasResource {

    private final Logger log = LoggerFactory.getLogger(ProgramasResource.class);

    private static final String ENTITY_NAME = "programas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProgramasRepository programasRepository;

    public ProgramasResource(ProgramasRepository programasRepository) {
        this.programasRepository = programasRepository;
    }

    /**
     * {@code POST  /programas} : Create a new programas.
     *
     * @param programas the programas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new programas, or with status {@code 400 (Bad Request)} if the programas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/programas")
    public ResponseEntity<Programas> createProgramas(@RequestBody Programas programas) throws URISyntaxException {
        log.debug("REST request to save Programas : {}", programas);
        if (programas.getId() != null) {
            throw new BadRequestAlertException("A new programas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Programas result = programasRepository.save(programas);
        return ResponseEntity
            .created(new URI("/api/programas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /programas/:id} : Updates an existing programas.
     *
     * @param id the id of the programas to save.
     * @param programas the programas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programas,
     * or with status {@code 400 (Bad Request)} if the programas is not valid,
     * or with status {@code 500 (Internal Server Error)} if the programas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/programas/{id}")
    public ResponseEntity<Programas> updateProgramas(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Programas programas
    ) throws URISyntaxException {
        log.debug("REST request to update Programas : {}, {}", id, programas);
        if (programas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, programas.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!programasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Programas result = programasRepository.save(programas);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, programas.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /programas/:id} : Partial updates given fields of an existing programas, field will ignore if it is null
     *
     * @param id the id of the programas to save.
     * @param programas the programas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programas,
     * or with status {@code 400 (Bad Request)} if the programas is not valid,
     * or with status {@code 404 (Not Found)} if the programas is not found,
     * or with status {@code 500 (Internal Server Error)} if the programas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/programas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Programas> partialUpdateProgramas(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Programas programas
    ) throws URISyntaxException {
        log.debug("REST request to partial update Programas partially : {}, {}", id, programas);
        if (programas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, programas.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!programasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Programas> result = programasRepository
            .findById(programas.getId())
            .map(existingProgramas -> {
                if (programas.getName() != null) {
                    existingProgramas.setName(programas.getName());
                }
                if (programas.getIdentificacion() != null) {
                    existingProgramas.setIdentificacion(programas.getIdentificacion());
                }
                if (programas.getfKConvenio() != null) {
                    existingProgramas.setfKConvenio(programas.getfKConvenio());
                }

                return existingProgramas;
            })
            .map(programasRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, programas.getId().toString())
        );
    }

    /**
     * {@code GET  /programas} : get all the programas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of programas in body.
     */
    @GetMapping("/programas")
    public List<Programas> getAllProgramas() {
        log.debug("REST request to get all Programas");
        return programasRepository.findAll();
    }

    /**
     * {@code GET  /programas/:id} : get the "id" programas.
     *
     * @param id the id of the programas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the programas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/programas/{id}")
    public ResponseEntity<Programas> getProgramas(@PathVariable Long id) {
        log.debug("REST request to get Programas : {}", id);
        Optional<Programas> programas = programasRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(programas);
    }

    @GetMapping("/programas/nit/{nit}")
    public ResponseEntity<Programas> getProgramassByNit(@PathVariable("nit") Long nit) {
        log.debug("REST request to get Programass : {}", nit);
        Optional<Programas> programa = programasRepository.getProgramaNameByNit(nit);
        return ResponseUtil.wrapOrNotFound(programa);
    }

    /**
     * {@code DELETE  /programas/:id} : delete the "id" programas.
     *
     * @param id the id of the programas to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/programas/{id}")
    public ResponseEntity<Void> deleteProgramas(@PathVariable Long id) {
        log.debug("REST request to delete Programas : {}", id);
        programasRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
