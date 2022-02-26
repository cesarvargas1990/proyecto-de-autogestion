package com.supergiros.portalautogestion.repository;

import com.supergiros.portalautogestion.domain.Departamentos;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Departamentos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartamentosRepository extends JpaRepository<Departamentos, Long> {
    @Query(value = "SELECT id FROM departamentos WHERE departamentos.name = ?1 ", nativeQuery = true)
    Long findIdByName(String departamentoName);
}
