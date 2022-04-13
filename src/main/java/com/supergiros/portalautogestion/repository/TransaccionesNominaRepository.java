package com.supergiros.portalautogestion.repository;

import com.supergiros.portalautogestion.domain.TransaccionesNomina;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TransaccionesNomina entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransaccionesNominaRepository extends JpaRepository<TransaccionesNomina, Long> {}
