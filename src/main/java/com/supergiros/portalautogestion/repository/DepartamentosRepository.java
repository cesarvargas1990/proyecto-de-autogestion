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

    @Query("SELECT d.codDane FROM Departamentos d " + "where d.id = :id ")
    String getDepartamentosCodDaneById(@Param("id") Long id);

    @Query(value = "SELECT id FROM departamentos WHERE departamentos.name = ?1 ", nativeQuery = true)
    Long findIdByName(String departamentoName);

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

    //TRAER INFO
    @Query(
        value = "SELECT DISTINCT name FROM jhi_user_location INNER JOIN departamentos ON (jhi_user_location.departamento_name) = CAST(departamentos.id as varchar) WHERE jhi_user_location.user_id = ?1 ",
        nativeQuery = true
    )
    //@Query(value = "SELECT departamentos.name, municipio.name FROM jhi_user_location INNER JOIN departamentos ON (jhi_user_location.departamento_name) = CAST(departamentos.id as varchar) INNER JOIN municipio ON (jhi_user_location.municipio_name) = CAST(municipio.id as varchar) WHERE jhi_user_location.user_id = ?1 ", nativeQuery = true)
    List<String> findDepartamentosNameByID(Long IdUser);

    @Query(value = "SELECT DISTINCT municipio.fk_departamento FROM municipio WHERE municipio.name = ?1 ", nativeQuery = true)
    String findDepartamentosIDByMunicipioName(String municipioName);

    //TRAER INFO ON INIT
    @Query(
        value = "SELECT DISTINCT name FROM jhi_user_location INNER JOIN municipio ON (jhi_user_location.municipio_name) = CAST(municipio.id as varchar) WHERE jhi_user_location.user_id = ?1 ",
        nativeQuery = true
    )
    List<String> findMunicipiosNameByID(Long IdUser);

    //TRAER INFO EDICIÓN
    @Query(
        value = "SELECT DISTINCT name FROM jhi_user_location INNER JOIN municipio ON (jhi_user_location.municipio_name) = CAST(municipio.id as varchar) WHERE jhi_user_location.user_id = ?1 AND municipio.fk_departamento= ?2 ",
        nativeQuery = true
    )
    List<String> findMunicipiosNameByIDAndDepartamento(Long IdUser, Long departamentoId);

    //TRAER Convenio
    @Query(
        value = "SELECT DISTINCT convenio.id FROM jhi_user INNER JOIN convenio ON (jhi_user.fk_convenio) = convenio.id  WHERE jhi_user.id = ?1 ",
        nativeQuery = true
    )
    Long findConvenioID(Long IdUser);

    //NO FUNCIONA
    @Query(
        value = "SELECT DISTINCT name FROM jhi_user INNER JOIN programas ON (jhi_user.fk_programa) = programas.id  WHERE jhi_user.id = ?1 ",
        nativeQuery = true
    )
    String findProgramaName(Long IdUser);

    //PRUEBA DE FUNCIONA
    @Query(
        value = "SELECT jhi_user.fk_programa FROM jhi_user INNER JOIN programas ON jhi_user.fk_programa = programas.id  WHERE jhi_user.id = ?1 AND programas.name= ?2 ",
        nativeQuery = true
    )
    Long findProgramasName(Long IdUser, String programaName);

    //DUDOSOS

    @Query(value = "SELECT name FROM municipio WHERE municipio.fk_departamento = ?1 ", nativeQuery = true)
    List<String> getMunicipiosName(Long fkDepartmanento);

    @Query(
        value = "SELECT DISTINCT municipio.fk_departamento FROM jhi_user_location INNER JOIN municipio ON (jhi_user_location.municipio_name) = CAST(municipio.id as varchar) WHERE municipio.name= ?1 ",
        nativeQuery = true
    )
    Long findDepartamentosIDByMunicipiosName(String municipioName);

    @Query(
        value = "SELECT DISTINCT municipio.fk_departamento FROM jhi_user_location INNER JOIN municipio ON (jhi_user_location.municipio_name) = CAST(municipio.id as varchar) WHERE municipio.name= ?1 ",
        nativeQuery = true
    )
    List<String> findMultiplesDepartamentosIDByMunicipiosName(String municipioName);

    @Query(value = "SELECT id FROM jhi_user WHERE jhi_user.login = ?1 AND jhi_user.document_type = ?1 ", nativeQuery = true)
    Boolean searchInDB(String login, String documentType);

    @Query(value = "SELECT name FROM convenio WHERE convenio.id = ?1 ", nativeQuery = true)
    List<String> findNameByIdConvenio(Long convenioId);

    @Query(value = "SELECT name FROM programas WHERE programas.id = ?1 ", nativeQuery = true)
    List<String> findNameByIdPrograma(Long programaId);

    @Query(value = "SELECT departamento_name FROM jhi_user_location WHERE jhi_user_location.user_id = ?1 ", nativeQuery = true)
    List<String> findNameByIdDepartamento(Long departamentoId);

    @Query(value = "SELECT municipio_name FROM jhi_user_location WHERE jhi_user_location.user_id = ?1 ", nativeQuery = true)
    List<String> findNameByIdMunicipio(Long municipioName);

    @Query(
        value = "SELECT D.cod_dane FROM jhi_user " +
        "as U INNER JOIN jhi_user_location" +
        " as L ON U.id = L.user_id " +
        "INNER JOIN departamentos as D " +
        "ON CAST ( L.departamento_name AS BIGINT ) = D.id" +
        " WHERE U.id= ?1",
        nativeQuery = true
    )
    List<String> findCodDaneUserList(int userId);
}
