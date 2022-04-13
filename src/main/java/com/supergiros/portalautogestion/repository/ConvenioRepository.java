package com.supergiros.portalautogestion.repository;

import com.supergiros.portalautogestion.domain.Convenio;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Convenio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConvenioRepository extends JpaRepository<Convenio, Long> {
    @Query("SELECT c FROM Convenio c " + "where c.identificacion = :nit ")
    Optional<Convenio> getConvenioNameByNit(@Param("nit") String nit);
}
