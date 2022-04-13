package com.supergiros.portalautogestion.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.supergiros.portalautogestion.IntegrationTest;
import com.supergiros.portalautogestion.domain.TransaccionesNomina;
import com.supergiros.portalautogestion.repository.TransaccionesNominaRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link TransaccionesNominaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TransaccionesNominaResourceIT {

    private static final String DEFAULT_TIPO_DOCUMENTO_BENEF = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_DOCUMENTO_BENEF = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_DOCUMENTO_BENEF = 1;
    private static final Integer UPDATED_NUMERO_DOCUMENTO_BENEF = 2;

    private static final String DEFAULT_NOMBRE_UNO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_UNO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_DOS = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_DOS = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_UNO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_UNO = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_DOS = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_DOS = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_UNO_PAGO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_UNO_PAGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_DOS_PAGO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_DOS_PAGO = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_UNO_PAGO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_UNO_PAGO = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_DOS_PAGO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_DOS_PAGO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_PAGO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_PAGO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_HORA_PAGO = "AAAAAAAAAA";
    private static final String UPDATED_HORA_PAGO = "BBBBBBBBBB";

    private static final Integer DEFAULT_PIN_PAGO = 1;
    private static final Integer UPDATED_PIN_PAGO = 2;

    private static final Integer DEFAULT_F_K_DEPARTAMENTO_DE_PAGO = 1;
    private static final Integer UPDATED_F_K_DEPARTAMENTO_DE_PAGO = 2;

    private static final Integer DEFAULT_F_K_MUNICIPIO_DE_PAGO = 1;
    private static final Integer UPDATED_F_K_MUNICIPIO_DE_PAGO = 2;

    private static final Integer DEFAULT_F_K_DEPARTAMENTO = 1;
    private static final Integer UPDATED_F_K_DEPARTAMENTO = 2;

    private static final Integer DEFAULT_F_K_MUNICIPIO = 1;
    private static final Integer UPDATED_F_K_MUNICIPIO = 2;

    private static final Integer DEFAULT_F_K_ID_CONVENIO = 1;
    private static final Integer UPDATED_F_K_ID_CONVENIO = 2;

    private static final Integer DEFAULT_F_K_ID_PROGRAMA = 1;
    private static final Integer UPDATED_F_K_ID_PROGRAMA = 2;

    private static final LocalDate DEFAULT_FECHA_DE_PAGO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_DE_PAGO = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_VALOR_GIRO = 1;
    private static final Integer UPDATED_VALOR_GIRO = 2;

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String DEFAULT_PERIODO_PAGO = "AAAAAAAAAA";
    private static final String UPDATED_PERIODO_PAGO = "BBBBBBBBBB";

    private static final String DEFAULT_MOTIVO_ANULACION = "AAAAAAAAAA";
    private static final String UPDATED_MOTIVO_ANULACION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_VIGENCIA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_VIGENCIA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_CARGUE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_CARGUE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOTA = "AAAAAAAAAA";
    private static final String UPDATED_NOTA = "BBBBBBBBBB";

    private static final String DEFAULT_RED_PAGADORA = "AAAAAAAAAA";
    private static final String UPDATED_RED_PAGADORA = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACION_CONTROL = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACION_CONTROL = "BBBBBBBBBB";

    private static final String DEFAULT_SOLICITUD_AUTORIZACION = "AAAAAAAAAA";
    private static final String UPDATED_SOLICITUD_AUTORIZACION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/transacciones-nominas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TransaccionesNominaRepository transaccionesNominaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransaccionesNominaMockMvc;

    private TransaccionesNomina transaccionesNomina;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransaccionesNomina createEntity(EntityManager em) {
        TransaccionesNomina transaccionesNomina = new TransaccionesNomina()
            .tipoDocumentoBenef(DEFAULT_TIPO_DOCUMENTO_BENEF)
            .numeroDocumentoBenef(DEFAULT_NUMERO_DOCUMENTO_BENEF)
            .nombreUno(DEFAULT_NOMBRE_UNO)
            .nombreDos(DEFAULT_NOMBRE_DOS)
            .apellidoUno(DEFAULT_APELLIDO_UNO)
            .apellidoDos(DEFAULT_APELLIDO_DOS)
            .nombreUnoPago(DEFAULT_NOMBRE_UNO_PAGO)
            .nombreDosPago(DEFAULT_NOMBRE_DOS_PAGO)
            .apellidoUnoPago(DEFAULT_APELLIDO_UNO_PAGO)
            .apellidoDosPago(DEFAULT_APELLIDO_DOS_PAGO)
            .fechaPago(DEFAULT_FECHA_PAGO)
            .horaPago(DEFAULT_HORA_PAGO)
            .pinPago(DEFAULT_PIN_PAGO)
            .fKDepartamentoDePago(DEFAULT_F_K_DEPARTAMENTO_DE_PAGO)
            .fKMunicipioDePago(DEFAULT_F_K_MUNICIPIO_DE_PAGO)
            .fKDepartamento(DEFAULT_F_K_DEPARTAMENTO)
            .fKMunicipio(DEFAULT_F_K_MUNICIPIO)
            .fKIdConvenio(DEFAULT_F_K_ID_CONVENIO)
            .fKIdPrograma(DEFAULT_F_K_ID_PROGRAMA)
            .fechaDePago(DEFAULT_FECHA_DE_PAGO)
            .valorGiro(DEFAULT_VALOR_GIRO)
            .estado(DEFAULT_ESTADO)
            .periodoPago(DEFAULT_PERIODO_PAGO)
            .motivoAnulacion(DEFAULT_MOTIVO_ANULACION)
            .fechaVigencia(DEFAULT_FECHA_VIGENCIA)
            .fechaCargue(DEFAULT_FECHA_CARGUE)
            .nota(DEFAULT_NOTA)
            .redPagadora(DEFAULT_RED_PAGADORA)
            .observacionControl(DEFAULT_OBSERVACION_CONTROL)
            .solicitudAutorizacion(DEFAULT_SOLICITUD_AUTORIZACION);
        return transaccionesNomina;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransaccionesNomina createUpdatedEntity(EntityManager em) {
        TransaccionesNomina transaccionesNomina = new TransaccionesNomina()
            .tipoDocumentoBenef(UPDATED_TIPO_DOCUMENTO_BENEF)
            .numeroDocumentoBenef(UPDATED_NUMERO_DOCUMENTO_BENEF)
            .nombreUno(UPDATED_NOMBRE_UNO)
            .nombreDos(UPDATED_NOMBRE_DOS)
            .apellidoUno(UPDATED_APELLIDO_UNO)
            .apellidoDos(UPDATED_APELLIDO_DOS)
            .nombreUnoPago(UPDATED_NOMBRE_UNO_PAGO)
            .nombreDosPago(UPDATED_NOMBRE_DOS_PAGO)
            .apellidoUnoPago(UPDATED_APELLIDO_UNO_PAGO)
            .apellidoDosPago(UPDATED_APELLIDO_DOS_PAGO)
            .fechaPago(UPDATED_FECHA_PAGO)
            .horaPago(UPDATED_HORA_PAGO)
            .pinPago(UPDATED_PIN_PAGO)
            .fKDepartamentoDePago(UPDATED_F_K_DEPARTAMENTO_DE_PAGO)
            .fKMunicipioDePago(UPDATED_F_K_MUNICIPIO_DE_PAGO)
            .fKDepartamento(UPDATED_F_K_DEPARTAMENTO)
            .fKMunicipio(UPDATED_F_K_MUNICIPIO)
            .fKIdConvenio(UPDATED_F_K_ID_CONVENIO)
            .fKIdPrograma(UPDATED_F_K_ID_PROGRAMA)
            .fechaDePago(UPDATED_FECHA_DE_PAGO)
            .valorGiro(UPDATED_VALOR_GIRO)
            .estado(UPDATED_ESTADO)
            .periodoPago(UPDATED_PERIODO_PAGO)
            .motivoAnulacion(UPDATED_MOTIVO_ANULACION)
            .fechaVigencia(UPDATED_FECHA_VIGENCIA)
            .fechaCargue(UPDATED_FECHA_CARGUE)
            .nota(UPDATED_NOTA)
            .redPagadora(UPDATED_RED_PAGADORA)
            .observacionControl(UPDATED_OBSERVACION_CONTROL)
            .solicitudAutorizacion(UPDATED_SOLICITUD_AUTORIZACION);
        return transaccionesNomina;
    }

    @BeforeEach
    public void initTest() {
        transaccionesNomina = createEntity(em);
    }

    @Test
    @Transactional
    void createTransaccionesNomina() throws Exception {
        int databaseSizeBeforeCreate = transaccionesNominaRepository.findAll().size();
        // Create the TransaccionesNomina
        restTransaccionesNominaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transaccionesNomina))
            )
            .andExpect(status().isCreated());

        // Validate the TransaccionesNomina in the database
        List<TransaccionesNomina> transaccionesNominaList = transaccionesNominaRepository.findAll();
        assertThat(transaccionesNominaList).hasSize(databaseSizeBeforeCreate + 1);
        TransaccionesNomina testTransaccionesNomina = transaccionesNominaList.get(transaccionesNominaList.size() - 1);
        assertThat(testTransaccionesNomina.getTipoDocumentoBenef()).isEqualTo(DEFAULT_TIPO_DOCUMENTO_BENEF);
        assertThat(testTransaccionesNomina.getNumeroDocumentoBenef()).isEqualTo(DEFAULT_NUMERO_DOCUMENTO_BENEF);
        assertThat(testTransaccionesNomina.getNombreUno()).isEqualTo(DEFAULT_NOMBRE_UNO);
        assertThat(testTransaccionesNomina.getNombreDos()).isEqualTo(DEFAULT_NOMBRE_DOS);
        assertThat(testTransaccionesNomina.getApellidoUno()).isEqualTo(DEFAULT_APELLIDO_UNO);
        assertThat(testTransaccionesNomina.getApellidoDos()).isEqualTo(DEFAULT_APELLIDO_DOS);
        assertThat(testTransaccionesNomina.getNombreUnoPago()).isEqualTo(DEFAULT_NOMBRE_UNO_PAGO);
        assertThat(testTransaccionesNomina.getNombreDosPago()).isEqualTo(DEFAULT_NOMBRE_DOS_PAGO);
        assertThat(testTransaccionesNomina.getApellidoUnoPago()).isEqualTo(DEFAULT_APELLIDO_UNO_PAGO);
        assertThat(testTransaccionesNomina.getApellidoDosPago()).isEqualTo(DEFAULT_APELLIDO_DOS_PAGO);
        assertThat(testTransaccionesNomina.getFechaPago()).isEqualTo(DEFAULT_FECHA_PAGO);
        assertThat(testTransaccionesNomina.getHoraPago()).isEqualTo(DEFAULT_HORA_PAGO);
        assertThat(testTransaccionesNomina.getPinPago()).isEqualTo(DEFAULT_PIN_PAGO);
        assertThat(testTransaccionesNomina.getfKDepartamentoDePago()).isEqualTo(DEFAULT_F_K_DEPARTAMENTO_DE_PAGO);
        assertThat(testTransaccionesNomina.getfKMunicipioDePago()).isEqualTo(DEFAULT_F_K_MUNICIPIO_DE_PAGO);
        assertThat(testTransaccionesNomina.getfKDepartamento()).isEqualTo(DEFAULT_F_K_DEPARTAMENTO);
        assertThat(testTransaccionesNomina.getfKMunicipio()).isEqualTo(DEFAULT_F_K_MUNICIPIO);
        assertThat(testTransaccionesNomina.getfKIdConvenio()).isEqualTo(DEFAULT_F_K_ID_CONVENIO);
        assertThat(testTransaccionesNomina.getfKIdPrograma()).isEqualTo(DEFAULT_F_K_ID_PROGRAMA);
        assertThat(testTransaccionesNomina.getFechaDePago()).isEqualTo(DEFAULT_FECHA_DE_PAGO);
        assertThat(testTransaccionesNomina.getValorGiro()).isEqualTo(DEFAULT_VALOR_GIRO);
        assertThat(testTransaccionesNomina.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testTransaccionesNomina.getPeriodoPago()).isEqualTo(DEFAULT_PERIODO_PAGO);
        assertThat(testTransaccionesNomina.getMotivoAnulacion()).isEqualTo(DEFAULT_MOTIVO_ANULACION);
        assertThat(testTransaccionesNomina.getFechaVigencia()).isEqualTo(DEFAULT_FECHA_VIGENCIA);
        assertThat(testTransaccionesNomina.getFechaCargue()).isEqualTo(DEFAULT_FECHA_CARGUE);
        assertThat(testTransaccionesNomina.getNota()).isEqualTo(DEFAULT_NOTA);
        assertThat(testTransaccionesNomina.getRedPagadora()).isEqualTo(DEFAULT_RED_PAGADORA);
        assertThat(testTransaccionesNomina.getObservacionControl()).isEqualTo(DEFAULT_OBSERVACION_CONTROL);
        assertThat(testTransaccionesNomina.getSolicitudAutorizacion()).isEqualTo(DEFAULT_SOLICITUD_AUTORIZACION);
    }

    @Test
    @Transactional
    void createTransaccionesNominaWithExistingId() throws Exception {
        // Create the TransaccionesNomina with an existing ID
        transaccionesNomina.setId(1L);

        int databaseSizeBeforeCreate = transaccionesNominaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransaccionesNominaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transaccionesNomina))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransaccionesNomina in the database
        List<TransaccionesNomina> transaccionesNominaList = transaccionesNominaRepository.findAll();
        assertThat(transaccionesNominaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTransaccionesNominas() throws Exception {
        // Initialize the database
        transaccionesNominaRepository.saveAndFlush(transaccionesNomina);

        // Get all the transaccionesNominaList
        restTransaccionesNominaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaccionesNomina.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoDocumentoBenef").value(hasItem(DEFAULT_TIPO_DOCUMENTO_BENEF)))
            .andExpect(jsonPath("$.[*].numeroDocumentoBenef").value(hasItem(DEFAULT_NUMERO_DOCUMENTO_BENEF)))
            .andExpect(jsonPath("$.[*].nombreUno").value(hasItem(DEFAULT_NOMBRE_UNO)))
            .andExpect(jsonPath("$.[*].nombreDos").value(hasItem(DEFAULT_NOMBRE_DOS)))
            .andExpect(jsonPath("$.[*].apellidoUno").value(hasItem(DEFAULT_APELLIDO_UNO)))
            .andExpect(jsonPath("$.[*].apellidoDos").value(hasItem(DEFAULT_APELLIDO_DOS)))
            .andExpect(jsonPath("$.[*].nombreUnoPago").value(hasItem(DEFAULT_NOMBRE_UNO_PAGO)))
            .andExpect(jsonPath("$.[*].nombreDosPago").value(hasItem(DEFAULT_NOMBRE_DOS_PAGO)))
            .andExpect(jsonPath("$.[*].apellidoUnoPago").value(hasItem(DEFAULT_APELLIDO_UNO_PAGO)))
            .andExpect(jsonPath("$.[*].apellidoDosPago").value(hasItem(DEFAULT_APELLIDO_DOS_PAGO)))
            .andExpect(jsonPath("$.[*].fechaPago").value(hasItem(DEFAULT_FECHA_PAGO.toString())))
            .andExpect(jsonPath("$.[*].horaPago").value(hasItem(DEFAULT_HORA_PAGO)))
            .andExpect(jsonPath("$.[*].pinPago").value(hasItem(DEFAULT_PIN_PAGO)))
            .andExpect(jsonPath("$.[*].fKDepartamentoDePago").value(hasItem(DEFAULT_F_K_DEPARTAMENTO_DE_PAGO)))
            .andExpect(jsonPath("$.[*].fKMunicipioDePago").value(hasItem(DEFAULT_F_K_MUNICIPIO_DE_PAGO)))
            .andExpect(jsonPath("$.[*].fKDepartamento").value(hasItem(DEFAULT_F_K_DEPARTAMENTO)))
            .andExpect(jsonPath("$.[*].fKMunicipio").value(hasItem(DEFAULT_F_K_MUNICIPIO)))
            .andExpect(jsonPath("$.[*].fKIdConvenio").value(hasItem(DEFAULT_F_K_ID_CONVENIO)))
            .andExpect(jsonPath("$.[*].fKIdPrograma").value(hasItem(DEFAULT_F_K_ID_PROGRAMA)))
            .andExpect(jsonPath("$.[*].fechaDePago").value(hasItem(DEFAULT_FECHA_DE_PAGO.toString())))
            .andExpect(jsonPath("$.[*].valorGiro").value(hasItem(DEFAULT_VALOR_GIRO)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].periodoPago").value(hasItem(DEFAULT_PERIODO_PAGO)))
            .andExpect(jsonPath("$.[*].motivoAnulacion").value(hasItem(DEFAULT_MOTIVO_ANULACION)))
            .andExpect(jsonPath("$.[*].fechaVigencia").value(hasItem(DEFAULT_FECHA_VIGENCIA.toString())))
            .andExpect(jsonPath("$.[*].fechaCargue").value(hasItem(DEFAULT_FECHA_CARGUE.toString())))
            .andExpect(jsonPath("$.[*].nota").value(hasItem(DEFAULT_NOTA)))
            .andExpect(jsonPath("$.[*].redPagadora").value(hasItem(DEFAULT_RED_PAGADORA)))
            .andExpect(jsonPath("$.[*].observacionControl").value(hasItem(DEFAULT_OBSERVACION_CONTROL)))
            .andExpect(jsonPath("$.[*].solicitudAutorizacion").value(hasItem(DEFAULT_SOLICITUD_AUTORIZACION)));
    }

    @Test
    @Transactional
    void getTransaccionesNomina() throws Exception {
        // Initialize the database
        transaccionesNominaRepository.saveAndFlush(transaccionesNomina);

        // Get the transaccionesNomina
        restTransaccionesNominaMockMvc
            .perform(get(ENTITY_API_URL_ID, transaccionesNomina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transaccionesNomina.getId().intValue()))
            .andExpect(jsonPath("$.tipoDocumentoBenef").value(DEFAULT_TIPO_DOCUMENTO_BENEF))
            .andExpect(jsonPath("$.numeroDocumentoBenef").value(DEFAULT_NUMERO_DOCUMENTO_BENEF))
            .andExpect(jsonPath("$.nombreUno").value(DEFAULT_NOMBRE_UNO))
            .andExpect(jsonPath("$.nombreDos").value(DEFAULT_NOMBRE_DOS))
            .andExpect(jsonPath("$.apellidoUno").value(DEFAULT_APELLIDO_UNO))
            .andExpect(jsonPath("$.apellidoDos").value(DEFAULT_APELLIDO_DOS))
            .andExpect(jsonPath("$.nombreUnoPago").value(DEFAULT_NOMBRE_UNO_PAGO))
            .andExpect(jsonPath("$.nombreDosPago").value(DEFAULT_NOMBRE_DOS_PAGO))
            .andExpect(jsonPath("$.apellidoUnoPago").value(DEFAULT_APELLIDO_UNO_PAGO))
            .andExpect(jsonPath("$.apellidoDosPago").value(DEFAULT_APELLIDO_DOS_PAGO))
            .andExpect(jsonPath("$.fechaPago").value(DEFAULT_FECHA_PAGO.toString()))
            .andExpect(jsonPath("$.horaPago").value(DEFAULT_HORA_PAGO))
            .andExpect(jsonPath("$.pinPago").value(DEFAULT_PIN_PAGO))
            .andExpect(jsonPath("$.fKDepartamentoDePago").value(DEFAULT_F_K_DEPARTAMENTO_DE_PAGO))
            .andExpect(jsonPath("$.fKMunicipioDePago").value(DEFAULT_F_K_MUNICIPIO_DE_PAGO))
            .andExpect(jsonPath("$.fKDepartamento").value(DEFAULT_F_K_DEPARTAMENTO))
            .andExpect(jsonPath("$.fKMunicipio").value(DEFAULT_F_K_MUNICIPIO))
            .andExpect(jsonPath("$.fKIdConvenio").value(DEFAULT_F_K_ID_CONVENIO))
            .andExpect(jsonPath("$.fKIdPrograma").value(DEFAULT_F_K_ID_PROGRAMA))
            .andExpect(jsonPath("$.fechaDePago").value(DEFAULT_FECHA_DE_PAGO.toString()))
            .andExpect(jsonPath("$.valorGiro").value(DEFAULT_VALOR_GIRO))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.periodoPago").value(DEFAULT_PERIODO_PAGO))
            .andExpect(jsonPath("$.motivoAnulacion").value(DEFAULT_MOTIVO_ANULACION))
            .andExpect(jsonPath("$.fechaVigencia").value(DEFAULT_FECHA_VIGENCIA.toString()))
            .andExpect(jsonPath("$.fechaCargue").value(DEFAULT_FECHA_CARGUE.toString()))
            .andExpect(jsonPath("$.nota").value(DEFAULT_NOTA))
            .andExpect(jsonPath("$.redPagadora").value(DEFAULT_RED_PAGADORA))
            .andExpect(jsonPath("$.observacionControl").value(DEFAULT_OBSERVACION_CONTROL))
            .andExpect(jsonPath("$.solicitudAutorizacion").value(DEFAULT_SOLICITUD_AUTORIZACION));
    }

    @Test
    @Transactional
    void getNonExistingTransaccionesNomina() throws Exception {
        // Get the transaccionesNomina
        restTransaccionesNominaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTransaccionesNomina() throws Exception {
        // Initialize the database
        transaccionesNominaRepository.saveAndFlush(transaccionesNomina);

        int databaseSizeBeforeUpdate = transaccionesNominaRepository.findAll().size();

        // Update the transaccionesNomina
        TransaccionesNomina updatedTransaccionesNomina = transaccionesNominaRepository.findById(transaccionesNomina.getId()).get();
        // Disconnect from session so that the updates on updatedTransaccionesNomina are not directly saved in db
        em.detach(updatedTransaccionesNomina);
        updatedTransaccionesNomina
            .tipoDocumentoBenef(UPDATED_TIPO_DOCUMENTO_BENEF)
            .numeroDocumentoBenef(UPDATED_NUMERO_DOCUMENTO_BENEF)
            .nombreUno(UPDATED_NOMBRE_UNO)
            .nombreDos(UPDATED_NOMBRE_DOS)
            .apellidoUno(UPDATED_APELLIDO_UNO)
            .apellidoDos(UPDATED_APELLIDO_DOS)
            .nombreUnoPago(UPDATED_NOMBRE_UNO_PAGO)
            .nombreDosPago(UPDATED_NOMBRE_DOS_PAGO)
            .apellidoUnoPago(UPDATED_APELLIDO_UNO_PAGO)
            .apellidoDosPago(UPDATED_APELLIDO_DOS_PAGO)
            .fechaPago(UPDATED_FECHA_PAGO)
            .horaPago(UPDATED_HORA_PAGO)
            .pinPago(UPDATED_PIN_PAGO)
            .fKDepartamentoDePago(UPDATED_F_K_DEPARTAMENTO_DE_PAGO)
            .fKMunicipioDePago(UPDATED_F_K_MUNICIPIO_DE_PAGO)
            .fKDepartamento(UPDATED_F_K_DEPARTAMENTO)
            .fKMunicipio(UPDATED_F_K_MUNICIPIO)
            .fKIdConvenio(UPDATED_F_K_ID_CONVENIO)
            .fKIdPrograma(UPDATED_F_K_ID_PROGRAMA)
            .fechaDePago(UPDATED_FECHA_DE_PAGO)
            .valorGiro(UPDATED_VALOR_GIRO)
            .estado(UPDATED_ESTADO)
            .periodoPago(UPDATED_PERIODO_PAGO)
            .motivoAnulacion(UPDATED_MOTIVO_ANULACION)
            .fechaVigencia(UPDATED_FECHA_VIGENCIA)
            .fechaCargue(UPDATED_FECHA_CARGUE)
            .nota(UPDATED_NOTA)
            .redPagadora(UPDATED_RED_PAGADORA)
            .observacionControl(UPDATED_OBSERVACION_CONTROL)
            .solicitudAutorizacion(UPDATED_SOLICITUD_AUTORIZACION);

        restTransaccionesNominaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTransaccionesNomina.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTransaccionesNomina))
            )
            .andExpect(status().isOk());

        // Validate the TransaccionesNomina in the database
        List<TransaccionesNomina> transaccionesNominaList = transaccionesNominaRepository.findAll();
        assertThat(transaccionesNominaList).hasSize(databaseSizeBeforeUpdate);
        TransaccionesNomina testTransaccionesNomina = transaccionesNominaList.get(transaccionesNominaList.size() - 1);
        assertThat(testTransaccionesNomina.getTipoDocumentoBenef()).isEqualTo(UPDATED_TIPO_DOCUMENTO_BENEF);
        assertThat(testTransaccionesNomina.getNumeroDocumentoBenef()).isEqualTo(UPDATED_NUMERO_DOCUMENTO_BENEF);
        assertThat(testTransaccionesNomina.getNombreUno()).isEqualTo(UPDATED_NOMBRE_UNO);
        assertThat(testTransaccionesNomina.getNombreDos()).isEqualTo(UPDATED_NOMBRE_DOS);
        assertThat(testTransaccionesNomina.getApellidoUno()).isEqualTo(UPDATED_APELLIDO_UNO);
        assertThat(testTransaccionesNomina.getApellidoDos()).isEqualTo(UPDATED_APELLIDO_DOS);
        assertThat(testTransaccionesNomina.getNombreUnoPago()).isEqualTo(UPDATED_NOMBRE_UNO_PAGO);
        assertThat(testTransaccionesNomina.getNombreDosPago()).isEqualTo(UPDATED_NOMBRE_DOS_PAGO);
        assertThat(testTransaccionesNomina.getApellidoUnoPago()).isEqualTo(UPDATED_APELLIDO_UNO_PAGO);
        assertThat(testTransaccionesNomina.getApellidoDosPago()).isEqualTo(UPDATED_APELLIDO_DOS_PAGO);
        assertThat(testTransaccionesNomina.getFechaPago()).isEqualTo(UPDATED_FECHA_PAGO);
        assertThat(testTransaccionesNomina.getHoraPago()).isEqualTo(UPDATED_HORA_PAGO);
        assertThat(testTransaccionesNomina.getPinPago()).isEqualTo(UPDATED_PIN_PAGO);
        assertThat(testTransaccionesNomina.getfKDepartamentoDePago()).isEqualTo(UPDATED_F_K_DEPARTAMENTO_DE_PAGO);
        assertThat(testTransaccionesNomina.getfKMunicipioDePago()).isEqualTo(UPDATED_F_K_MUNICIPIO_DE_PAGO);
        assertThat(testTransaccionesNomina.getfKDepartamento()).isEqualTo(UPDATED_F_K_DEPARTAMENTO);
        assertThat(testTransaccionesNomina.getfKMunicipio()).isEqualTo(UPDATED_F_K_MUNICIPIO);
        assertThat(testTransaccionesNomina.getfKIdConvenio()).isEqualTo(UPDATED_F_K_ID_CONVENIO);
        assertThat(testTransaccionesNomina.getfKIdPrograma()).isEqualTo(UPDATED_F_K_ID_PROGRAMA);
        assertThat(testTransaccionesNomina.getFechaDePago()).isEqualTo(UPDATED_FECHA_DE_PAGO);
        assertThat(testTransaccionesNomina.getValorGiro()).isEqualTo(UPDATED_VALOR_GIRO);
        assertThat(testTransaccionesNomina.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testTransaccionesNomina.getPeriodoPago()).isEqualTo(UPDATED_PERIODO_PAGO);
        assertThat(testTransaccionesNomina.getMotivoAnulacion()).isEqualTo(UPDATED_MOTIVO_ANULACION);
        assertThat(testTransaccionesNomina.getFechaVigencia()).isEqualTo(UPDATED_FECHA_VIGENCIA);
        assertThat(testTransaccionesNomina.getFechaCargue()).isEqualTo(UPDATED_FECHA_CARGUE);
        assertThat(testTransaccionesNomina.getNota()).isEqualTo(UPDATED_NOTA);
        assertThat(testTransaccionesNomina.getRedPagadora()).isEqualTo(UPDATED_RED_PAGADORA);
        assertThat(testTransaccionesNomina.getObservacionControl()).isEqualTo(UPDATED_OBSERVACION_CONTROL);
        assertThat(testTransaccionesNomina.getSolicitudAutorizacion()).isEqualTo(UPDATED_SOLICITUD_AUTORIZACION);
    }

    @Test
    @Transactional
    void putNonExistingTransaccionesNomina() throws Exception {
        int databaseSizeBeforeUpdate = transaccionesNominaRepository.findAll().size();
        transaccionesNomina.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransaccionesNominaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transaccionesNomina.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transaccionesNomina))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransaccionesNomina in the database
        List<TransaccionesNomina> transaccionesNominaList = transaccionesNominaRepository.findAll();
        assertThat(transaccionesNominaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTransaccionesNomina() throws Exception {
        int databaseSizeBeforeUpdate = transaccionesNominaRepository.findAll().size();
        transaccionesNomina.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransaccionesNominaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transaccionesNomina))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransaccionesNomina in the database
        List<TransaccionesNomina> transaccionesNominaList = transaccionesNominaRepository.findAll();
        assertThat(transaccionesNominaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTransaccionesNomina() throws Exception {
        int databaseSizeBeforeUpdate = transaccionesNominaRepository.findAll().size();
        transaccionesNomina.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransaccionesNominaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transaccionesNomina))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransaccionesNomina in the database
        List<TransaccionesNomina> transaccionesNominaList = transaccionesNominaRepository.findAll();
        assertThat(transaccionesNominaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransaccionesNominaWithPatch() throws Exception {
        // Initialize the database
        transaccionesNominaRepository.saveAndFlush(transaccionesNomina);

        int databaseSizeBeforeUpdate = transaccionesNominaRepository.findAll().size();

        // Update the transaccionesNomina using partial update
        TransaccionesNomina partialUpdatedTransaccionesNomina = new TransaccionesNomina();
        partialUpdatedTransaccionesNomina.setId(transaccionesNomina.getId());

        partialUpdatedTransaccionesNomina
            .tipoDocumentoBenef(UPDATED_TIPO_DOCUMENTO_BENEF)
            .numeroDocumentoBenef(UPDATED_NUMERO_DOCUMENTO_BENEF)
            .nombreUno(UPDATED_NOMBRE_UNO)
            .apellidoDos(UPDATED_APELLIDO_DOS)
            .nombreUnoPago(UPDATED_NOMBRE_UNO_PAGO)
            .nombreDosPago(UPDATED_NOMBRE_DOS_PAGO)
            .apellidoDosPago(UPDATED_APELLIDO_DOS_PAGO)
            .horaPago(UPDATED_HORA_PAGO)
            .pinPago(UPDATED_PIN_PAGO)
            .fKMunicipioDePago(UPDATED_F_K_MUNICIPIO_DE_PAGO)
            .fKDepartamento(UPDATED_F_K_DEPARTAMENTO)
            .fKMunicipio(UPDATED_F_K_MUNICIPIO)
            .fechaDePago(UPDATED_FECHA_DE_PAGO)
            .valorGiro(UPDATED_VALOR_GIRO)
            .periodoPago(UPDATED_PERIODO_PAGO)
            .fechaVigencia(UPDATED_FECHA_VIGENCIA)
            .redPagadora(UPDATED_RED_PAGADORA);

        restTransaccionesNominaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransaccionesNomina.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransaccionesNomina))
            )
            .andExpect(status().isOk());

        // Validate the TransaccionesNomina in the database
        List<TransaccionesNomina> transaccionesNominaList = transaccionesNominaRepository.findAll();
        assertThat(transaccionesNominaList).hasSize(databaseSizeBeforeUpdate);
        TransaccionesNomina testTransaccionesNomina = transaccionesNominaList.get(transaccionesNominaList.size() - 1);
        assertThat(testTransaccionesNomina.getTipoDocumentoBenef()).isEqualTo(UPDATED_TIPO_DOCUMENTO_BENEF);
        assertThat(testTransaccionesNomina.getNumeroDocumentoBenef()).isEqualTo(UPDATED_NUMERO_DOCUMENTO_BENEF);
        assertThat(testTransaccionesNomina.getNombreUno()).isEqualTo(UPDATED_NOMBRE_UNO);
        assertThat(testTransaccionesNomina.getNombreDos()).isEqualTo(DEFAULT_NOMBRE_DOS);
        assertThat(testTransaccionesNomina.getApellidoUno()).isEqualTo(DEFAULT_APELLIDO_UNO);
        assertThat(testTransaccionesNomina.getApellidoDos()).isEqualTo(UPDATED_APELLIDO_DOS);
        assertThat(testTransaccionesNomina.getNombreUnoPago()).isEqualTo(UPDATED_NOMBRE_UNO_PAGO);
        assertThat(testTransaccionesNomina.getNombreDosPago()).isEqualTo(UPDATED_NOMBRE_DOS_PAGO);
        assertThat(testTransaccionesNomina.getApellidoUnoPago()).isEqualTo(DEFAULT_APELLIDO_UNO_PAGO);
        assertThat(testTransaccionesNomina.getApellidoDosPago()).isEqualTo(UPDATED_APELLIDO_DOS_PAGO);
        assertThat(testTransaccionesNomina.getFechaPago()).isEqualTo(DEFAULT_FECHA_PAGO);
        assertThat(testTransaccionesNomina.getHoraPago()).isEqualTo(UPDATED_HORA_PAGO);
        assertThat(testTransaccionesNomina.getPinPago()).isEqualTo(UPDATED_PIN_PAGO);
        assertThat(testTransaccionesNomina.getfKDepartamentoDePago()).isEqualTo(DEFAULT_F_K_DEPARTAMENTO_DE_PAGO);
        assertThat(testTransaccionesNomina.getfKMunicipioDePago()).isEqualTo(UPDATED_F_K_MUNICIPIO_DE_PAGO);
        assertThat(testTransaccionesNomina.getfKDepartamento()).isEqualTo(UPDATED_F_K_DEPARTAMENTO);
        assertThat(testTransaccionesNomina.getfKMunicipio()).isEqualTo(UPDATED_F_K_MUNICIPIO);
        assertThat(testTransaccionesNomina.getfKIdConvenio()).isEqualTo(DEFAULT_F_K_ID_CONVENIO);
        assertThat(testTransaccionesNomina.getfKIdPrograma()).isEqualTo(DEFAULT_F_K_ID_PROGRAMA);
        assertThat(testTransaccionesNomina.getFechaDePago()).isEqualTo(UPDATED_FECHA_DE_PAGO);
        assertThat(testTransaccionesNomina.getValorGiro()).isEqualTo(UPDATED_VALOR_GIRO);
        assertThat(testTransaccionesNomina.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testTransaccionesNomina.getPeriodoPago()).isEqualTo(UPDATED_PERIODO_PAGO);
        assertThat(testTransaccionesNomina.getMotivoAnulacion()).isEqualTo(DEFAULT_MOTIVO_ANULACION);
        assertThat(testTransaccionesNomina.getFechaVigencia()).isEqualTo(UPDATED_FECHA_VIGENCIA);
        assertThat(testTransaccionesNomina.getFechaCargue()).isEqualTo(DEFAULT_FECHA_CARGUE);
        assertThat(testTransaccionesNomina.getNota()).isEqualTo(DEFAULT_NOTA);
        assertThat(testTransaccionesNomina.getRedPagadora()).isEqualTo(UPDATED_RED_PAGADORA);
        assertThat(testTransaccionesNomina.getObservacionControl()).isEqualTo(DEFAULT_OBSERVACION_CONTROL);
        assertThat(testTransaccionesNomina.getSolicitudAutorizacion()).isEqualTo(DEFAULT_SOLICITUD_AUTORIZACION);
    }

    @Test
    @Transactional
    void fullUpdateTransaccionesNominaWithPatch() throws Exception {
        // Initialize the database
        transaccionesNominaRepository.saveAndFlush(transaccionesNomina);

        int databaseSizeBeforeUpdate = transaccionesNominaRepository.findAll().size();

        // Update the transaccionesNomina using partial update
        TransaccionesNomina partialUpdatedTransaccionesNomina = new TransaccionesNomina();
        partialUpdatedTransaccionesNomina.setId(transaccionesNomina.getId());

        partialUpdatedTransaccionesNomina
            .tipoDocumentoBenef(UPDATED_TIPO_DOCUMENTO_BENEF)
            .numeroDocumentoBenef(UPDATED_NUMERO_DOCUMENTO_BENEF)
            .nombreUno(UPDATED_NOMBRE_UNO)
            .nombreDos(UPDATED_NOMBRE_DOS)
            .apellidoUno(UPDATED_APELLIDO_UNO)
            .apellidoDos(UPDATED_APELLIDO_DOS)
            .nombreUnoPago(UPDATED_NOMBRE_UNO_PAGO)
            .nombreDosPago(UPDATED_NOMBRE_DOS_PAGO)
            .apellidoUnoPago(UPDATED_APELLIDO_UNO_PAGO)
            .apellidoDosPago(UPDATED_APELLIDO_DOS_PAGO)
            .fechaPago(UPDATED_FECHA_PAGO)
            .horaPago(UPDATED_HORA_PAGO)
            .pinPago(UPDATED_PIN_PAGO)
            .fKDepartamentoDePago(UPDATED_F_K_DEPARTAMENTO_DE_PAGO)
            .fKMunicipioDePago(UPDATED_F_K_MUNICIPIO_DE_PAGO)
            .fKDepartamento(UPDATED_F_K_DEPARTAMENTO)
            .fKMunicipio(UPDATED_F_K_MUNICIPIO)
            .fKIdConvenio(UPDATED_F_K_ID_CONVENIO)
            .fKIdPrograma(UPDATED_F_K_ID_PROGRAMA)
            .fechaDePago(UPDATED_FECHA_DE_PAGO)
            .valorGiro(UPDATED_VALOR_GIRO)
            .estado(UPDATED_ESTADO)
            .periodoPago(UPDATED_PERIODO_PAGO)
            .motivoAnulacion(UPDATED_MOTIVO_ANULACION)
            .fechaVigencia(UPDATED_FECHA_VIGENCIA)
            .fechaCargue(UPDATED_FECHA_CARGUE)
            .nota(UPDATED_NOTA)
            .redPagadora(UPDATED_RED_PAGADORA)
            .observacionControl(UPDATED_OBSERVACION_CONTROL)
            .solicitudAutorizacion(UPDATED_SOLICITUD_AUTORIZACION);

        restTransaccionesNominaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransaccionesNomina.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransaccionesNomina))
            )
            .andExpect(status().isOk());

        // Validate the TransaccionesNomina in the database
        List<TransaccionesNomina> transaccionesNominaList = transaccionesNominaRepository.findAll();
        assertThat(transaccionesNominaList).hasSize(databaseSizeBeforeUpdate);
        TransaccionesNomina testTransaccionesNomina = transaccionesNominaList.get(transaccionesNominaList.size() - 1);
        assertThat(testTransaccionesNomina.getTipoDocumentoBenef()).isEqualTo(UPDATED_TIPO_DOCUMENTO_BENEF);
        assertThat(testTransaccionesNomina.getNumeroDocumentoBenef()).isEqualTo(UPDATED_NUMERO_DOCUMENTO_BENEF);
        assertThat(testTransaccionesNomina.getNombreUno()).isEqualTo(UPDATED_NOMBRE_UNO);
        assertThat(testTransaccionesNomina.getNombreDos()).isEqualTo(UPDATED_NOMBRE_DOS);
        assertThat(testTransaccionesNomina.getApellidoUno()).isEqualTo(UPDATED_APELLIDO_UNO);
        assertThat(testTransaccionesNomina.getApellidoDos()).isEqualTo(UPDATED_APELLIDO_DOS);
        assertThat(testTransaccionesNomina.getNombreUnoPago()).isEqualTo(UPDATED_NOMBRE_UNO_PAGO);
        assertThat(testTransaccionesNomina.getNombreDosPago()).isEqualTo(UPDATED_NOMBRE_DOS_PAGO);
        assertThat(testTransaccionesNomina.getApellidoUnoPago()).isEqualTo(UPDATED_APELLIDO_UNO_PAGO);
        assertThat(testTransaccionesNomina.getApellidoDosPago()).isEqualTo(UPDATED_APELLIDO_DOS_PAGO);
        assertThat(testTransaccionesNomina.getFechaPago()).isEqualTo(UPDATED_FECHA_PAGO);
        assertThat(testTransaccionesNomina.getHoraPago()).isEqualTo(UPDATED_HORA_PAGO);
        assertThat(testTransaccionesNomina.getPinPago()).isEqualTo(UPDATED_PIN_PAGO);
        assertThat(testTransaccionesNomina.getfKDepartamentoDePago()).isEqualTo(UPDATED_F_K_DEPARTAMENTO_DE_PAGO);
        assertThat(testTransaccionesNomina.getfKMunicipioDePago()).isEqualTo(UPDATED_F_K_MUNICIPIO_DE_PAGO);
        assertThat(testTransaccionesNomina.getfKDepartamento()).isEqualTo(UPDATED_F_K_DEPARTAMENTO);
        assertThat(testTransaccionesNomina.getfKMunicipio()).isEqualTo(UPDATED_F_K_MUNICIPIO);
        assertThat(testTransaccionesNomina.getfKIdConvenio()).isEqualTo(UPDATED_F_K_ID_CONVENIO);
        assertThat(testTransaccionesNomina.getfKIdPrograma()).isEqualTo(UPDATED_F_K_ID_PROGRAMA);
        assertThat(testTransaccionesNomina.getFechaDePago()).isEqualTo(UPDATED_FECHA_DE_PAGO);
        assertThat(testTransaccionesNomina.getValorGiro()).isEqualTo(UPDATED_VALOR_GIRO);
        assertThat(testTransaccionesNomina.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testTransaccionesNomina.getPeriodoPago()).isEqualTo(UPDATED_PERIODO_PAGO);
        assertThat(testTransaccionesNomina.getMotivoAnulacion()).isEqualTo(UPDATED_MOTIVO_ANULACION);
        assertThat(testTransaccionesNomina.getFechaVigencia()).isEqualTo(UPDATED_FECHA_VIGENCIA);
        assertThat(testTransaccionesNomina.getFechaCargue()).isEqualTo(UPDATED_FECHA_CARGUE);
        assertThat(testTransaccionesNomina.getNota()).isEqualTo(UPDATED_NOTA);
        assertThat(testTransaccionesNomina.getRedPagadora()).isEqualTo(UPDATED_RED_PAGADORA);
        assertThat(testTransaccionesNomina.getObservacionControl()).isEqualTo(UPDATED_OBSERVACION_CONTROL);
        assertThat(testTransaccionesNomina.getSolicitudAutorizacion()).isEqualTo(UPDATED_SOLICITUD_AUTORIZACION);
    }

    @Test
    @Transactional
    void patchNonExistingTransaccionesNomina() throws Exception {
        int databaseSizeBeforeUpdate = transaccionesNominaRepository.findAll().size();
        transaccionesNomina.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransaccionesNominaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transaccionesNomina.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transaccionesNomina))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransaccionesNomina in the database
        List<TransaccionesNomina> transaccionesNominaList = transaccionesNominaRepository.findAll();
        assertThat(transaccionesNominaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTransaccionesNomina() throws Exception {
        int databaseSizeBeforeUpdate = transaccionesNominaRepository.findAll().size();
        transaccionesNomina.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransaccionesNominaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transaccionesNomina))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransaccionesNomina in the database
        List<TransaccionesNomina> transaccionesNominaList = transaccionesNominaRepository.findAll();
        assertThat(transaccionesNominaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTransaccionesNomina() throws Exception {
        int databaseSizeBeforeUpdate = transaccionesNominaRepository.findAll().size();
        transaccionesNomina.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransaccionesNominaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transaccionesNomina))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransaccionesNomina in the database
        List<TransaccionesNomina> transaccionesNominaList = transaccionesNominaRepository.findAll();
        assertThat(transaccionesNominaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTransaccionesNomina() throws Exception {
        // Initialize the database
        transaccionesNominaRepository.saveAndFlush(transaccionesNomina);

        int databaseSizeBeforeDelete = transaccionesNominaRepository.findAll().size();

        // Delete the transaccionesNomina
        restTransaccionesNominaMockMvc
            .perform(delete(ENTITY_API_URL_ID, transaccionesNomina.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransaccionesNomina> transaccionesNominaList = transaccionesNominaRepository.findAll();
        assertThat(transaccionesNominaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
