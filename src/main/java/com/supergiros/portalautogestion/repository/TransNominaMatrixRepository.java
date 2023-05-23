package com.supergiros.portalautogestion.repository;

import com.supergiros.portalautogestion.domain.TransaccionesNomina;
import com.supergiros.portalautogestion.domain.TransaccionesNominaMatrix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransNominaMatrixRepository extends JpaRepository<TransaccionesNominaMatrix, Long> {

    @Query(
        "SELECT t FROM TransaccionesNominaMatrix t " +
            "where t.tipoDocumentoBenef = :typeDocument " +
            "and t.numeroDocumentoBenef = :numberDocument " +
            "and t.idPrograma = :programa "
         
    )
    List<TransaccionesNominaMatrix> findByTypeDocumentAndNumberDocumentUser(
        @Param("typeDocument") String typeDocument,
        @Param("numberDocument") Long numberDocument,
        @Param("programa") String programa
    );

    @Query(
        "SELECT t FROM TransaccionesNominaMatrix t " +
            "where t.tipoDocumentoBenef = :typeDocument " +
            "and t.numeroDocumentoBenef = :numberDocument " +
            "and t.idPrograma = :programa "
           // "and t.observacionControl = :idNomina"
    )
    List<TransaccionesNominaMatrix> findByTypeDocumentAndNumerDocumentAllDepartmentsUser(
        @Param("typeDocument") String typeDocument,
        @Param("numberDocument") Long numberDocument,
        @Param("programa") String programa
       // @Param("idNomina") String idNomina
    );

    @Query(
        "SELECT t FROM TransaccionesNominaMatrix t " +
            "where t.tipoDocumentoBenef = :typeDocument " +
            "and t.numeroDocumentoBenef = :numberDocument "
            //"and t.observacionControl = :idNomina"
    )
    List<TransaccionesNominaMatrix> findByTypeDocument(
        @Param("typeDocument") String typeDocument,
        @Param("numberDocument") Long numberDocument
    );

    @Query(
        "SELECT t FROM TransaccionesNominaMatrix t " +
            "where t.tipoDocumentoBenef = :typeDocument " +
            "and t.numeroDocumentoBenef = :numberDocument"
    )
    List<TransaccionesNominaMatrix> findByTypeDocumentAndNumberDocumentAdmin(
        @Param("typeDocument") String typeDocument,
        @Param("numberDocument") Long numberDocument
    );


}
