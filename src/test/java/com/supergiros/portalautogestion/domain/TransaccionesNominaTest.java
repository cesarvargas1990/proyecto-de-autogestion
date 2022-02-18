package com.supergiros.portalautogestion.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.supergiros.portalautogestion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TransaccionesNominaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransaccionesNomina.class);
        TransaccionesNomina transaccionesNomina1 = new TransaccionesNomina();
        transaccionesNomina1.setId(1L);
        TransaccionesNomina transaccionesNomina2 = new TransaccionesNomina();
        transaccionesNomina2.setId(transaccionesNomina1.getId());
        assertThat(transaccionesNomina1).isEqualTo(transaccionesNomina2);
        transaccionesNomina2.setId(2L);
        assertThat(transaccionesNomina1).isNotEqualTo(transaccionesNomina2);
        transaccionesNomina1.setId(null);
        assertThat(transaccionesNomina1).isNotEqualTo(transaccionesNomina2);
    }
}
