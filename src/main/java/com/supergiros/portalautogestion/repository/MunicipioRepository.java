package com.supergiros.portalautogestion.repository;

import com.supergiros.portalautogestion.domain.Municipio;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Municipio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Long> {
    @Query("SELECT m FROM Municipio m " + "where m.codDane = :codDane ")
    Optional<Municipio> getMunicipioNameByCodDane(@Param("codDane") Integer codDane);
}
