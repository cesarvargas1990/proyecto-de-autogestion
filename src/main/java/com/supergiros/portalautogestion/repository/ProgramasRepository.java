package com.supergiros.portalautogestion.repository;

import com.supergiros.portalautogestion.domain.Programas;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Programas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgramasRepository extends JpaRepository<Programas, Long> {}
