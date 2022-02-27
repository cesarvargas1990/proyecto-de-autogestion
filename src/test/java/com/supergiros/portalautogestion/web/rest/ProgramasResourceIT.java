package com.supergiros.portalautogestion.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.supergiros.portalautogestion.IntegrationTest;
import com.supergiros.portalautogestion.domain.Programas;
import com.supergiros.portalautogestion.repository.ProgramasRepository;
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
 * Integration tests for the {@link ProgramasResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProgramasResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICACION = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICACION = "BBBBBBBBBB";

    private static final String DEFAULT_F_K_CONVENIO = "AAAAAAAAAA";
    private static final String UPDATED_F_K_CONVENIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/programas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProgramasRepository programasRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProgramasMockMvc;

    private Programas programas;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Programas createEntity(EntityManager em) {
        Programas programas = new Programas().name(DEFAULT_NAME).identificacion(DEFAULT_IDENTIFICACION).fKConvenio(DEFAULT_F_K_CONVENIO);
        return programas;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Programas createUpdatedEntity(EntityManager em) {
        Programas programas = new Programas().name(UPDATED_NAME).identificacion(UPDATED_IDENTIFICACION).fKConvenio(UPDATED_F_K_CONVENIO);
        return programas;
    }

    @BeforeEach
    public void initTest() {
        programas = createEntity(em);
    }

    @Test
    @Transactional
    void createProgramas() throws Exception {
        int databaseSizeBeforeCreate = programasRepository.findAll().size();
        // Create the Programas
        restProgramasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(programas)))
            .andExpect(status().isCreated());

        // Validate the Programas in the database
        List<Programas> programasList = programasRepository.findAll();
        assertThat(programasList).hasSize(databaseSizeBeforeCreate + 1);
        Programas testProgramas = programasList.get(programasList.size() - 1);
        assertThat(testProgramas.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProgramas.getIdentificacion()).isEqualTo(DEFAULT_IDENTIFICACION);
        assertThat(testProgramas.getfKConvenio()).isEqualTo(DEFAULT_F_K_CONVENIO);
    }

    @Test
    @Transactional
    void createProgramasWithExistingId() throws Exception {
        // Create the Programas with an existing ID
        programas.setId(1L);

        int databaseSizeBeforeCreate = programasRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgramasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(programas)))
            .andExpect(status().isBadRequest());

        // Validate the Programas in the database
        List<Programas> programasList = programasRepository.findAll();
        assertThat(programasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProgramas() throws Exception {
        // Initialize the database
        programasRepository.saveAndFlush(programas);

        // Get all the programasList
        restProgramasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(programas.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].identificacion").value(hasItem(DEFAULT_IDENTIFICACION)))
            .andExpect(jsonPath("$.[*].fKConvenio").value(hasItem(DEFAULT_F_K_CONVENIO)));
    }

    @Test
    @Transactional
    void getProgramas() throws Exception {
        // Initialize the database
        programasRepository.saveAndFlush(programas);

        // Get the programas
        restProgramasMockMvc
            .perform(get(ENTITY_API_URL_ID, programas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(programas.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.identificacion").value(DEFAULT_IDENTIFICACION))
            .andExpect(jsonPath("$.fKConvenio").value(DEFAULT_F_K_CONVENIO));
    }

    @Test
    @Transactional
    void getNonExistingProgramas() throws Exception {
        // Get the programas
        restProgramasMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProgramas() throws Exception {
        // Initialize the database
        programasRepository.saveAndFlush(programas);

        int databaseSizeBeforeUpdate = programasRepository.findAll().size();

        // Update the programas
        Programas updatedProgramas = programasRepository.findById(programas.getId()).get();
        // Disconnect from session so that the updates on updatedProgramas are not directly saved in db
        em.detach(updatedProgramas);
        updatedProgramas.name(UPDATED_NAME).identificacion(UPDATED_IDENTIFICACION).fKConvenio(UPDATED_F_K_CONVENIO);

        restProgramasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProgramas.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProgramas))
            )
            .andExpect(status().isOk());

        // Validate the Programas in the database
        List<Programas> programasList = programasRepository.findAll();
        assertThat(programasList).hasSize(databaseSizeBeforeUpdate);
        Programas testProgramas = programasList.get(programasList.size() - 1);
        assertThat(testProgramas.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProgramas.getIdentificacion()).isEqualTo(UPDATED_IDENTIFICACION);
        assertThat(testProgramas.getfKConvenio()).isEqualTo(UPDATED_F_K_CONVENIO);
    }

    @Test
    @Transactional
    void putNonExistingProgramas() throws Exception {
        int databaseSizeBeforeUpdate = programasRepository.findAll().size();
        programas.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, programas.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(programas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Programas in the database
        List<Programas> programasList = programasRepository.findAll();
        assertThat(programasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProgramas() throws Exception {
        int databaseSizeBeforeUpdate = programasRepository.findAll().size();
        programas.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(programas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Programas in the database
        List<Programas> programasList = programasRepository.findAll();
        assertThat(programasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProgramas() throws Exception {
        int databaseSizeBeforeUpdate = programasRepository.findAll().size();
        programas.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramasMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(programas)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Programas in the database
        List<Programas> programasList = programasRepository.findAll();
        assertThat(programasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProgramasWithPatch() throws Exception {
        // Initialize the database
        programasRepository.saveAndFlush(programas);

        int databaseSizeBeforeUpdate = programasRepository.findAll().size();

        // Update the programas using partial update
        Programas partialUpdatedProgramas = new Programas();
        partialUpdatedProgramas.setId(programas.getId());

        partialUpdatedProgramas.identificacion(UPDATED_IDENTIFICACION).fKConvenio(UPDATED_F_K_CONVENIO);

        restProgramasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgramas.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProgramas))
            )
            .andExpect(status().isOk());

        // Validate the Programas in the database
        List<Programas> programasList = programasRepository.findAll();
        assertThat(programasList).hasSize(databaseSizeBeforeUpdate);
        Programas testProgramas = programasList.get(programasList.size() - 1);
        assertThat(testProgramas.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProgramas.getIdentificacion()).isEqualTo(UPDATED_IDENTIFICACION);
        assertThat(testProgramas.getfKConvenio()).isEqualTo(UPDATED_F_K_CONVENIO);
    }

    @Test
    @Transactional
    void fullUpdateProgramasWithPatch() throws Exception {
        // Initialize the database
        programasRepository.saveAndFlush(programas);

        int databaseSizeBeforeUpdate = programasRepository.findAll().size();

        // Update the programas using partial update
        Programas partialUpdatedProgramas = new Programas();
        partialUpdatedProgramas.setId(programas.getId());

        partialUpdatedProgramas.name(UPDATED_NAME).identificacion(UPDATED_IDENTIFICACION).fKConvenio(UPDATED_F_K_CONVENIO);

        restProgramasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgramas.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProgramas))
            )
            .andExpect(status().isOk());

        // Validate the Programas in the database
        List<Programas> programasList = programasRepository.findAll();
        assertThat(programasList).hasSize(databaseSizeBeforeUpdate);
        Programas testProgramas = programasList.get(programasList.size() - 1);
        assertThat(testProgramas.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProgramas.getIdentificacion()).isEqualTo(UPDATED_IDENTIFICACION);
        assertThat(testProgramas.getfKConvenio()).isEqualTo(UPDATED_F_K_CONVENIO);
    }

    @Test
    @Transactional
    void patchNonExistingProgramas() throws Exception {
        int databaseSizeBeforeUpdate = programasRepository.findAll().size();
        programas.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, programas.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(programas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Programas in the database
        List<Programas> programasList = programasRepository.findAll();
        assertThat(programasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProgramas() throws Exception {
        int databaseSizeBeforeUpdate = programasRepository.findAll().size();
        programas.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(programas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Programas in the database
        List<Programas> programasList = programasRepository.findAll();
        assertThat(programasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProgramas() throws Exception {
        int databaseSizeBeforeUpdate = programasRepository.findAll().size();
        programas.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramasMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(programas))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Programas in the database
        List<Programas> programasList = programasRepository.findAll();
        assertThat(programasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProgramas() throws Exception {
        // Initialize the database
        programasRepository.saveAndFlush(programas);

        int databaseSizeBeforeDelete = programasRepository.findAll().size();

        // Delete the programas
        restProgramasMockMvc
            .perform(delete(ENTITY_API_URL_ID, programas.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Programas> programasList = programasRepository.findAll();
        assertThat(programasList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
