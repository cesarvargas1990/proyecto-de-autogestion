package com.supergiros.portalautogestion.service.mapper;

import com.supergiros.portalautogestion.domain.Convenio;
import com.supergiros.portalautogestion.domain.Departamentos;
import com.supergiros.portalautogestion.domain.Municipio;
import com.supergiros.portalautogestion.domain.Programas;
import com.supergiros.portalautogestion.domain.TransaccionesNomina;
import com.supergiros.portalautogestion.repository.ConvenioRepository;
import com.supergiros.portalautogestion.repository.DepartamentosRepository;
import com.supergiros.portalautogestion.repository.MunicipioRepository;
import com.supergiros.portalautogestion.repository.ProgramasRepository;
import com.supergiros.portalautogestion.service.dto.TransaccionesNominaListDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class transaccionesNominaMapper {

    @Autowired
    DepartamentosRepository departamentosRepository;

    @Autowired
    MunicipioRepository municipioRepository;

    @Autowired
    ConvenioRepository convenioRepository;

    @Autowired
    ProgramasRepository programaRepository;

    public List<TransaccionesNominaListDTO> transaccionesNominaMap(List<TransaccionesNomina> transaccionesNomina) {
        List<TransaccionesNominaListDTO> transaccionesNominas2 = new ArrayList<TransaccionesNominaListDTO>();
        for (int i = 0; i < transaccionesNomina.size(); i++) {
            TransaccionesNominaListDTO transaccionesWithChange = new TransaccionesNominaListDTO();

            transaccionesWithChange.setTipoDocumentoBenef(transaccionesNomina.get(i).getTipoDocumentoBenef());
            transaccionesWithChange.setNumeroDocumentoBenef(transaccionesNomina.get(i).getNumeroDocumentoBenef());
            transaccionesWithChange.setFechaPago(transaccionesNomina.get(i).getFechaPago());
            transaccionesWithChange.setHoraPago(transaccionesNomina.get(i).getHoraPago());
            transaccionesWithChange.setPinPago(transaccionesNomina.get(i).getPinPago());
            transaccionesWithChange.setfKDepartamentoDePago(transaccionesNomina.get(i).getfKDepartamentoDePago());
            transaccionesWithChange.setfKMunicipioDePago(transaccionesNomina.get(i).getfKMunicipioDePago());
            transaccionesWithChange.setfKIdConvenio(transaccionesNomina.get(i).getfKIdConvenio());
            transaccionesWithChange.setfKIdPrograma(transaccionesNomina.get(i).getfKIdPrograma());
            transaccionesWithChange.setValorGiro(transaccionesNomina.get(i).getValorGiro());
            transaccionesWithChange.setEstado(transaccionesNomina.get(i).getEstado());
            transaccionesWithChange.setMotivoAnulacion(transaccionesNomina.get(i).getMotivoAnulacion());
            transaccionesWithChange.setIdNomina(transaccionesNomina.get(i).getObservacionControl());
            transaccionesWithChange.setAgenciaOficinaNombre(transaccionesNomina.get(i).getAgenciaOficinaNombre());
            transaccionesWithChange.setNombreUnoPago(transaccionesNomina.get(i).getNombreUnoPago());
            transaccionesWithChange.setNombreDosPago(transaccionesNomina.get(i).getNombreDosPago());
            transaccionesWithChange.setApellidoUnoPago(transaccionesNomina.get(i).getApellidoUnoPago());
            transaccionesWithChange.setApellidoDosPago(transaccionesNomina.get(i).getApellidoDosPago());
            if (transaccionesNomina.get(i).getfKDepartamentoDePago() != null && transaccionesNomina.get(i).getfKMunicipioDePago() != null) {
                Departamentos departamentosNombre = departamentosRepository
                    .getDepartamentosNameByCodDane(Integer.parseInt(transaccionesNomina.get(i).getfKDepartamentoDePago()))
                    .orElse(null);
                Municipio municipiosNombre = municipioRepository
                    .getMunicipioNameByCodDane(Integer.parseInt(transaccionesNomina.get(i).getfKMunicipioDePago()))
                    .orElse(null);
                transaccionesWithChange.setfKMunicipioDePago(municipiosNombre.getName());
                transaccionesWithChange.setfKDepartamentoDePago(departamentosNombre.getName());
            }
            if (transaccionesNomina.get(i).getfKIdConvenio() != null && transaccionesNomina.get(i).getfKIdPrograma() != null) {
                Programas programasNombre = programaRepository
                    .getProgramaNameByNit(transaccionesNomina.get(i).getfKIdPrograma())
                    .orElse(null);
                Convenio conveniosNombre = convenioRepository
                    .getConvenioNameByNit(transaccionesNomina.get(i).getfKIdConvenio())
                    .orElse(null);
                transaccionesWithChange.setfKIdConvenio(conveniosNombre.getName());
                transaccionesWithChange.setfKIdPrograma(programasNombre.getName());
            }

            transaccionesNominas2.add(transaccionesWithChange);
        }
        return transaccionesNominas2;
    }
}
