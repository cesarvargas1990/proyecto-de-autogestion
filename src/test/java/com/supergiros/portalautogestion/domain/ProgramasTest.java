package com.supergiros.portalautogestion.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.supergiros.portalautogestion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProgramasTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Programas.class);
        Programas programas1 = new Programas();
        programas1.setId(1L);
        Programas programas2 = new Programas();
        programas2.setId(programas1.getId());
        assertThat(programas1).isEqualTo(programas2);
        programas2.setId(2L);
        assertThat(programas1).isNotEqualTo(programas2);
        programas1.setId(null);
        assertThat(programas1).isNotEqualTo(programas2);
    }
}
