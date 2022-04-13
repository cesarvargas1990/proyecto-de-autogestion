package com.supergiros.portalautogestion.repository;

import com.supergiros.portalautogestion.domain.Programas;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Programas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgramasRepository extends JpaRepository<Programas, Long> {
    @Query("SELECT p FROM Programas p " + "where p.identificacion = :nit ")
    Optional<Programas> getProgramaNameByNit(@Param("nit") String nit);
}
