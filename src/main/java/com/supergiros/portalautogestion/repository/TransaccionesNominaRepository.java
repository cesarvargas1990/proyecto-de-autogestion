package com.supergiros.portalautogestion.repository;

import com.supergiros.portalautogestion.domain.TransaccionesNomina;
import com.supergiros.portalautogestion.service.dto.RespuestaDTO;
import com.supergiros.portalautogestion.service.dto.TransaccionesNominaDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TransaccionesNomina entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransaccionesNominaRepository extends JpaRepository<TransaccionesNomina, Long> {
    @Modifying(clearAutomatically = true)
    @Query(
        "UPDATE TransaccionesNomina t " +
        "SET t.estado = :estado, " +
        "t.fKDepartamentoDePago = :departamento, " +
        "t.fKMunicipioDePago = :municipio, " +
        "t.nombreUnoPago = :nombrePagoUno, " +
        "t.nombreDosPago = :nombrePagoDos, " +
        "t.apellidoUnoPago = :apellidoPagoUno, " +
        "t.apellidoDosPago = :apellidoPagoDos, " +
        "t.fechaPago = :fechaPago, " +
        "t.horaPago = :horaPago, " +
        "t.pinPago = :pinPago, " +
        "t.motivoAnulacion = :motivoAnulacion " +
        "where t.tipoDocumentoBenef = :tipoDocumento " +
        "and t.numeroDocumentoBenef = :numeroDocumento " +
        "and t.periodoPago = :periodoDePago " +
        "and t.valorGiro = :valorPago " +
        "and t.fKIdPrograma = :programa " +
        "and t.fKIdConvenio = :convenio"
    )
    int updateTransaccionesNomina(
        @Param("estado") String estado,
        @Param("periodoDePago") String periodoDePago,
        @Param("tipoDocumento") String tipoDocumento,
        @Param("numeroDocumento") Integer numeroDocumento,
        @Param("departamento") String departamento,
        @Param("municipio") String municipio,
        @Param("pinPago") String pinPago,
        @Param("convenio") String convenio,
        @Param("programa") String programa,
        @Param("nombrePagoUno") String nombrePago1,
        @Param("nombrePagoDos") String nombrePago2,
        @Param("apellidoPagoUno") String apellidoPago1,
        @Param("apellidoPagoDos") String apellidoPago2,
        @Param("fechaPago") LocalDate fechaPago,
        @Param("horaPago") String horaPago,
        @Param("valorPago") String valorPago,
        @Param("motivoAnulacion") String motivoAnulacio
    );

    @Query(
        "SELECT t FROM TransaccionesNomina t " +
        "where t.tipoDocumentoBenef = :typeDocument " +
        "and t.numeroDocumentoBenef = :numberDocument " +
        "and t.fKDepartamento = :department " +
        "and t.fKIdConvenio = :convenio "
    )
    List<TransaccionesNomina> findByTypeDocumentAndNumerDocument(
        @Param("typeDocument") String typeDocument,
        @Param("numberDocument") Integer numberDocument,
        @Param("department") String department,
        @Param("convenio") String convenio
    );

    @Query(
        "SELECT t FROM TransaccionesNomina t " +
        "where t.tipoDocumentoBenef = :typeDocument " +
        "and t.numeroDocumentoBenef = :numberDocument"
    )
    List<TransaccionesNomina> findByTypeDocumentAndNumerDocumentAllDepartments(
        @Param("typeDocument") String typeDocument,
        @Param("numberDocument") Integer numberDocument
    );
}
