package com.supergiros.portalautogestion.web.rest;

import com.supergiros.portalautogestion.domain.Convenio;
import com.supergiros.portalautogestion.repository.ConvenioRepository;
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
 * REST controller for managing {@link com.supergiros.portalautogestion.domain.Convenio}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ConvenioResource {

    private final Logger log = LoggerFactory.getLogger(ConvenioResource.class);

    private static final String ENTITY_NAME = "convenio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConvenioRepository convenioRepository;

    public ConvenioResource(ConvenioRepository convenioRepository) {
        this.convenioRepository = convenioRepository;
    }

    /**
     * {@code POST  /convenios} : Create a new convenio.
     *
     * @param convenio the convenio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new convenio, or with status {@code 400 (Bad Request)} if the convenio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/convenios")
    public ResponseEntity<Convenio> createConvenio(@RequestBody Convenio convenio) throws URISyntaxException {
        log.debug("REST request to save Convenio : {}", convenio);
        if (convenio.getId() != null) {
            throw new BadRequestAlertException("A new convenio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Convenio result = convenioRepository.save(convenio);
        return ResponseEntity
            .created(new URI("/api/convenios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /convenios/:id} : Updates an existing convenio.
     *
     * @param id the id of the convenio to save.
     * @param convenio the convenio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated convenio,
     * or with status {@code 400 (Bad Request)} if the convenio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the convenio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/convenios/{id}")
    public ResponseEntity<Convenio> updateConvenio(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Convenio convenio
    ) throws URISyntaxException {
        log.debug("REST request to update Convenio : {}, {}", id, convenio);
        if (convenio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, convenio.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!convenioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Convenio result = convenioRepository.save(convenio);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, convenio.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /convenios/:id} : Partial updates given fields of an existing convenio, field will ignore if it is null
     *
     * @param id the id of the convenio to save.
     * @param convenio the convenio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated convenio,
     * or with status {@code 400 (Bad Request)} if the convenio is not valid,
     * or with status {@code 404 (Not Found)} if the convenio is not found,
     * or with status {@code 500 (Internal Server Error)} if the convenio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/convenios/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Convenio> partialUpdateConvenio(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Convenio convenio
    ) throws URISyntaxException {
        log.debug("REST request to partial update Convenio partially : {}, {}", id, convenio);
        if (convenio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, convenio.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!convenioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Convenio> result = convenioRepository
            .findById(convenio.getId())
            .map(existingConvenio -> {
                if (convenio.getName() != null) {
                    existingConvenio.setName(convenio.getName());
                }
                if (convenio.getIdentificacion() != null) {
                    existingConvenio.setIdentificacion(convenio.getIdentificacion());
                }

                return existingConvenio;
            })
            .map(convenioRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, convenio.getId().toString())
        );
    }

    /**
     * {@code GET  /convenios} : get all the convenios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of convenios in body.
     */
    @GetMapping("/convenios")
    public List<Convenio> getAllConvenios() {
        log.debug("REST request to get all Convenios");
        return convenioRepository.findAll();
    }

    /**
     * {@code GET  /convenios/:id} : get the "id" convenio.
     *
     * @param id the id of the convenio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the convenio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/convenios/{id}")
    public ResponseEntity<Convenio> getConvenio(@PathVariable Long id) {
        log.debug("REST request to get Convenio : {}", id);
        Optional<Convenio> convenio = convenioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(convenio);
    }

    /**
     * {@code DELETE  /convenios/:id} : delete the "id" convenio.
     *
     * @param id the id of the convenio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/convenios/{id}")
    public ResponseEntity<Void> deleteConvenio(@PathVariable Long id) {
        log.debug("REST request to delete Convenio : {}", id);
        convenioRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
