package com.supergiros.portalautogestion.repository;

import com.supergiros.portalautogestion.domain.Departamentos;
import com.supergiros.portalautogestion.service.dto.GrillaDTO;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Departamentos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartamentosRepository extends JpaRepository<Departamentos, Long> {
    List<Departamentos> findAll();

    @Query(value = "SELECT name FROM departamentos", nativeQuery = true)
    List<String> getDepartamentosName();

    @Query("SELECT d FROM Departamentos d " + "where d.codDane = :codDane ")
    Optional<Departamentos> getDepartamentosNameByCodDane(@Param("codDane") Integer codDane);

    @Query(value = "SELECT id FROM departamentos WHERE departamentos.name = ?1 ", nativeQuery = true)
    Long findIdByName(String departamentoName);

    @Query(value = "SELECT name FROM municipio WHERE municipio.fk_departamento = ?1 ", nativeQuery = true)
    List<String> getMunicipiosName(Long fkDepartmanento);

    @Query(value = "SELECT id FROM municipio WHERE municipio.name = ?1 ", nativeQuery = true)
    Long findIdByNameMunicipio(String municipioName);

    @Query(value = "SELECT name FROM convenio", nativeQuery = true)
    List<String> getConvenioName();

    @Query(value = "SELECT id FROM convenio WHERE convenio.name = ?1 ", nativeQuery = true)
    Long findIdByNameConvenio(String convenioName);

    @Query(value = "SELECT name FROM programas WHERE programas.fk_convenio = ?1 ", nativeQuery = true)
    List<String> getProgramaName(Long fkConvenio);

    @Query(value = "SELECT id FROM programas WHERE programas.name = ?1 ", nativeQuery = true)
    Long findIdByNamePrograma(String programaName);

    @Query(value = "SELECT id FROM jhi_user WHERE jhi_user.login = ?1 AND jhi_user.document_type = ?1 ", nativeQuery = true)
    Boolean searchInDB(String login, String documentType);
}
