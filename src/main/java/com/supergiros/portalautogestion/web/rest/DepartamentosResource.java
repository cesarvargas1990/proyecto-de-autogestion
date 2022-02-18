package com.supergiros.portalautogestion.web.rest;

import com.supergiros.portalautogestion.domain.Departamentos;
import com.supergiros.portalautogestion.repository.DepartamentosRepository;
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
 * REST controller for managing {@link com.supergiros.portalautogestion.domain.Departamentos}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DepartamentosResource {

    private final Logger log = LoggerFactory.getLogger(DepartamentosResource.class);

    private static final String ENTITY_NAME = "departamentos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepartamentosRepository departamentosRepository;

    public DepartamentosResource(DepartamentosRepository departamentosRepository) {
        this.departamentosRepository = departamentosRepository;
    }

    /**
     * {@code POST  /departamentos} : Create a new departamentos.
     *
     * @param departamentos the departamentos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new departamentos, or with status {@code 400 (Bad Request)} if the departamentos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/departamentos")
    public ResponseEntity<Departamentos> createDepartamentos(@RequestBody Departamentos departamentos) throws URISyntaxException {
        log.debug("REST request to save Departamentos : {}", departamentos);
        if (departamentos.getId() != null) {
            throw new BadRequestAlertException("A new departamentos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Departamentos result = departamentosRepository.save(departamentos);
        return ResponseEntity
            .created(new URI("/api/departamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /departamentos/:id} : Updates an existing departamentos.
     *
     * @param id the id of the departamentos to save.
     * @param departamentos the departamentos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated departamentos,
     * or with status {@code 400 (Bad Request)} if the departamentos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the departamentos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/departamentos/{id}")
    public ResponseEntity<Departamentos> updateDepartamentos(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Departamentos departamentos
    ) throws URISyntaxException {
        log.debug("REST request to update Departamentos : {}, {}", id, departamentos);
        if (departamentos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, departamentos.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!departamentosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Departamentos result = departamentosRepository.save(departamentos);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, departamentos.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /departamentos/:id} : Partial updates given fields of an existing departamentos, field will ignore if it is null
     *
     * @param id the id of the departamentos to save.
     * @param departamentos the departamentos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated departamentos,
     * or with status {@code 400 (Bad Request)} if the departamentos is not valid,
     * or with status {@code 404 (Not Found)} if the departamentos is not found,
     * or with status {@code 500 (Internal Server Error)} if the departamentos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/departamentos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Departamentos> partialUpdateDepartamentos(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Departamentos departamentos
    ) throws URISyntaxException {
        log.debug("REST request to partial update Departamentos partially : {}, {}", id, departamentos);
        if (departamentos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, departamentos.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!departamentosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Departamentos> result = departamentosRepository
            .findById(departamentos.getId())
            .map(existingDepartamentos -> {
                if (departamentos.getIdDepartamentos() != null) {
                    existingDepartamentos.setIdDepartamentos(departamentos.getIdDepartamentos());
                }
                if (departamentos.getName() != null) {
                    existingDepartamentos.setName(departamentos.getName());
                }
                if (departamentos.getCodDane() != null) {
                    existingDepartamentos.setCodDane(departamentos.getCodDane());
                }

                return existingDepartamentos;
            })
            .map(departamentosRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, departamentos.getId().toString())
        );
    }

    /**
     * {@code GET  /departamentos} : get all the departamentos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of departamentos in body.
     */
    @GetMapping("/departamentos")
    public List<Departamentos> getAllDepartamentos() {
        log.debug("REST request to get all Departamentos");
        return departamentosRepository.findAll();
    }

    /**
     * {@code GET  /departamentos/:id} : get the "id" departamentos.
     *
     * @param id the id of the departamentos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the departamentos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/departamentos/{id}")
    public ResponseEntity<Departamentos> getDepartamentos(@PathVariable Long id) {
        log.debug("REST request to get Departamentos : {}", id);
        Optional<Departamentos> departamentos = departamentosRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(departamentos);
    }

    /**
     * {@code DELETE  /departamentos/:id} : delete the "id" departamentos.
     *
     * @param id the id of the departamentos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/departamentos/{id}")
    public ResponseEntity<Void> deleteDepartamentos(@PathVariable Long id) {
        log.debug("REST request to delete Departamentos : {}", id);
        departamentosRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
