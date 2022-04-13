package com.supergiros.portalautogestion.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.supergiros.portalautogestion.IntegrationTest;
import com.supergiros.portalautogestion.domain.Departamentos;
import com.supergiros.portalautogestion.repository.DepartamentosRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DepartamentosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DepartamentosResourceIT {

    private static final Integer DEFAULT_ID_DEPARTAMENTOS = 1;
    private static final Integer UPDATED_ID_DEPARTAMENTOS = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_COD_DANE = 1;
    private static final Integer UPDATED_COD_DANE = 2;

    private static final String ENTITY_API_URL = "/api/departamentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DepartamentosRepository departamentosRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDepartamentosMockMvc;

    private Departamentos departamentos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Departamentos createEntity(EntityManager em) {
        Departamentos departamentos = new Departamentos()
            .idDepartamentos(DEFAULT_ID_DEPARTAMENTOS)
            .name(DEFAULT_NAME)
            .codDane(DEFAULT_COD_DANE);
        return departamentos;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Departamentos createUpdatedEntity(EntityManager em) {
        Departamentos departamentos = new Departamentos()
            .idDepartamentos(UPDATED_ID_DEPARTAMENTOS)
            .name(UPDATED_NAME)
            .codDane(UPDATED_COD_DANE);
        return departamentos;
    }

    @BeforeEach
    public void initTest() {
        departamentos = createEntity(em);
    }

    @Test
    @Transactional
    void createDepartamentos() throws Exception {
        int databaseSizeBeforeCreate = departamentosRepository.findAll().size();
        // Create the Departamentos
        restDepartamentosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(departamentos)))
            .andExpect(status().isCreated());

        // Validate the Departamentos in the database
        List<Departamentos> departamentosList = departamentosRepository.findAll();
        assertThat(departamentosList).hasSize(databaseSizeBeforeCreate + 1);
        Departamentos testDepartamentos = departamentosList.get(departamentosList.size() - 1);
        assertThat(testDepartamentos.getIdDepartamentos()).isEqualTo(DEFAULT_ID_DEPARTAMENTOS);
        assertThat(testDepartamentos.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDepartamentos.getCodDane()).isEqualTo(DEFAULT_COD_DANE);
    }

    @Test
    @Transactional
    void createDepartamentosWithExistingId() throws Exception {
        // Create the Departamentos with an existing ID
        departamentos.setId(1L);

        int databaseSizeBeforeCreate = departamentosRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartamentosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(departamentos)))
            .andExpect(status().isBadRequest());

        // Validate the Departamentos in the database
        List<Departamentos> departamentosList = departamentosRepository.findAll();
        assertThat(departamentosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDepartamentos() throws Exception {
        // Initialize the database
        departamentosRepository.saveAndFlush(departamentos);

        // Get all the departamentosList
        restDepartamentosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departamentos.getId().intValue())))
            .andExpect(jsonPath("$.[*].idDepartamentos").value(hasItem(DEFAULT_ID_DEPARTAMENTOS)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].codDane").value(hasItem(DEFAULT_COD_DANE)));
    }

    @Test
    @Transactional
    void getDepartamentos() throws Exception {
        // Initialize the database
        departamentosRepository.saveAndFlush(departamentos);

        // Get the departamentos
        restDepartamentosMockMvc
            .perform(get(ENTITY_API_URL_ID, departamentos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(departamentos.getId().intValue()))
            .andExpect(jsonPath("$.idDepartamentos").value(DEFAULT_ID_DEPARTAMENTOS))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.codDane").value(DEFAULT_COD_DANE));
    }

    @Test
    @Transactional
    void getNonExistingDepartamentos() throws Exception {
        // Get the departamentos
        restDepartamentosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDepartamentos() throws Exception {
        // Initialize the database
        departamentosRepository.saveAndFlush(departamentos);

        int databaseSizeBeforeUpdate = departamentosRepository.findAll().size();

        // Update the departamentos
        Departamentos updatedDepartamentos = departamentosRepository.findById(departamentos.getId()).get();
        // Disconnect from session so that the updates on updatedDepartamentos are not directly saved in db
        em.detach(updatedDepartamentos);
        updatedDepartamentos.idDepartamentos(UPDATED_ID_DEPARTAMENTOS).name(UPDATED_NAME).codDane(UPDATED_COD_DANE);

        restDepartamentosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDepartamentos.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDepartamentos))
            )
            .andExpect(status().isOk());

        // Validate the Departamentos in the database
        List<Departamentos> departamentosList = departamentosRepository.findAll();
        assertThat(departamentosList).hasSize(databaseSizeBeforeUpdate);
        Departamentos testDepartamentos = departamentosList.get(departamentosList.size() - 1);
        assertThat(testDepartamentos.getIdDepartamentos()).isEqualTo(UPDATED_ID_DEPARTAMENTOS);
        assertThat(testDepartamentos.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDepartamentos.getCodDane()).isEqualTo(UPDATED_COD_DANE);
    }

    @Test
    @Transactional
    void putNonExistingDepartamentos() throws Exception {
        int databaseSizeBeforeUpdate = departamentosRepository.findAll().size();
        departamentos.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartamentosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, departamentos.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(departamentos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Departamentos in the database
        List<Departamentos> departamentosList = departamentosRepository.findAll();
        assertThat(departamentosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDepartamentos() throws Exception {
        int databaseSizeBeforeUpdate = departamentosRepository.findAll().size();
        departamentos.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartamentosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(departamentos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Departamentos in the database
        List<Departamentos> departamentosList = departamentosRepository.findAll();
        assertThat(departamentosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDepartamentos() throws Exception {
        int databaseSizeBeforeUpdate = departamentosRepository.findAll().size();
        departamentos.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartamentosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(departamentos)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Departamentos in the database
        List<Departamentos> departamentosList = departamentosRepository.findAll();
        assertThat(departamentosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDepartamentosWithPatch() throws Exception {
        // Initialize the database
        departamentosRepository.saveAndFlush(departamentos);

        int databaseSizeBeforeUpdate = departamentosRepository.findAll().size();

        // Update the departamentos using partial update
        Departamentos partialUpdatedDepartamentos = new Departamentos();
        partialUpdatedDepartamentos.setId(departamentos.getId());

        partialUpdatedDepartamentos.name(UPDATED_NAME);

        restDepartamentosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDepartamentos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDepartamentos))
            )
            .andExpect(status().isOk());

        // Validate the Departamentos in the database
        List<Departamentos> departamentosList = departamentosRepository.findAll();
        assertThat(departamentosList).hasSize(databaseSizeBeforeUpdate);
        Departamentos testDepartamentos = departamentosList.get(departamentosList.size() - 1);
        assertThat(testDepartamentos.getIdDepartamentos()).isEqualTo(DEFAULT_ID_DEPARTAMENTOS);
        assertThat(testDepartamentos.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDepartamentos.getCodDane()).isEqualTo(DEFAULT_COD_DANE);
    }

    @Test
    @Transactional
    void fullUpdateDepartamentosWithPatch() throws Exception {
        // Initialize the database
        departamentosRepository.saveAndFlush(departamentos);

        int databaseSizeBeforeUpdate = departamentosRepository.findAll().size();

        // Update the departamentos using partial update
        Departamentos partialUpdatedDepartamentos = new Departamentos();
        partialUpdatedDepartamentos.setId(departamentos.getId());

        partialUpdatedDepartamentos.idDepartamentos(UPDATED_ID_DEPARTAMENTOS).name(UPDATED_NAME).codDane(UPDATED_COD_DANE);

        restDepartamentosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDepartamentos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDepartamentos))
            )
            .andExpect(status().isOk());

        // Validate the Departamentos in the database
        List<Departamentos> departamentosList = departamentosRepository.findAll();
        assertThat(departamentosList).hasSize(databaseSizeBeforeUpdate);
        Departamentos testDepartamentos = departamentosList.get(departamentosList.size() - 1);
        assertThat(testDepartamentos.getIdDepartamentos()).isEqualTo(UPDATED_ID_DEPARTAMENTOS);
        assertThat(testDepartamentos.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDepartamentos.getCodDane()).isEqualTo(UPDATED_COD_DANE);
    }

    @Test
    @Transactional
    void patchNonExistingDepartamentos() throws Exception {
        int databaseSizeBeforeUpdate = departamentosRepository.findAll().size();
        departamentos.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartamentosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, departamentos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(departamentos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Departamentos in the database
        List<Departamentos> departamentosList = departamentosRepository.findAll();
        assertThat(departamentosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDepartamentos() throws Exception {
        int databaseSizeBeforeUpdate = departamentosRepository.findAll().size();
        departamentos.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartamentosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(departamentos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Departamentos in the database
        List<Departamentos> departamentosList = departamentosRepository.findAll();
        assertThat(departamentosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDepartamentos() throws Exception {
        int databaseSizeBeforeUpdate = departamentosRepository.findAll().size();
        departamentos.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartamentosMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(departamentos))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Departamentos in the database
        List<Departamentos> departamentosList = departamentosRepository.findAll();
        assertThat(departamentosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDepartamentos() throws Exception {
        // Initialize the database
        departamentosRepository.saveAndFlush(departamentos);

        int databaseSizeBeforeDelete = departamentosRepository.findAll().size();

        // Delete the departamentos
        restDepartamentosMockMvc
            .perform(delete(ENTITY_API_URL_ID, departamentos.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Departamentos> departamentosList = departamentosRepository.findAll();
        assertThat(departamentosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
