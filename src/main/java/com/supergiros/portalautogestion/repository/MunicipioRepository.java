package com.supergiros.portalautogestion.repository;

import com.supergiros.portalautogestion.domain.Municipio;
import java.util.List;
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

    @Query(
        value = "SELECT M.cod_dane FROM jhi_user as U " +
        "INNER JOIN jhi_user_location as L " +
        "ON U.id = L.user_id " +
        "INNER JOIN municipio as M " +
        "ON CAST ( L.municipio_name AS BIGINT ) = M.id" +
        " WHERE U.id= ?1",
        nativeQuery = true
    )
    List<String> findCodDaneUserList(int userId);
}
