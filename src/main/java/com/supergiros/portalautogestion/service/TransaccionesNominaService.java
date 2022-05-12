package com.supergiros.portalautogestion.service;

import com.supergiros.portalautogestion.domain.TransaccionesNomina;
import com.supergiros.portalautogestion.repository.DepartamentosRepository;
import com.supergiros.portalautogestion.repository.TransaccionesNominaRepository;
import com.supergiros.portalautogestion.service.dto.TransaccionesNominaListDTO;
import com.supergiros.portalautogestion.service.mapper.transaccionesNominaMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransaccionesNominaService {

    @Autowired
    DepartamentosRepository departamentosRepository;

    @Autowired
    TransaccionesNominaRepository transaccionesNominaRepository;

    @Autowired
    transaccionesNominaMapper transaccionesNominaMapper;

    public List<String> findCodDaneDepartamentos(List<Long> departamentosIdLista) {
        List<String> idsLista = new ArrayList<>();
        for (int index = 0; index < departamentosIdLista.size(); index++) {
            idsLista.add(departamentosRepository.getDepartamentosCodDaneById(departamentosIdLista.get(index)));
        }
        return idsLista;
    }

    public List<TransaccionesNominaListDTO> searchTransacciones(
        String typeDocument,
        Integer numberDocument,
        String department,
        String programa,
        String idNomina
    ) {
        List<TransaccionesNomina> transaccionesNominas = new ArrayList<TransaccionesNomina>();
        if (!idNomina.equals("0")) {
            if (!programa.equals("99999")) {
                if (!department.equals("99999")) {
                    transaccionesNominas =
                        transaccionesNominaRepository.findByTypeDocumentAndNumerDocumentUser(
                            typeDocument,
                            numberDocument,
                            department,
                            programa,
                            idNomina
                        );
                } else {
                    transaccionesNominas =
                        transaccionesNominaRepository.findByTypeDocumentAndNumerDocumentAllDepartmentsUser(
                            typeDocument,
                            numberDocument,
                            programa,
                            idNomina
                        );
                }
            } else {
                transaccionesNominas =
                    transaccionesNominaRepository.findByTypeDocumentAndNumerDocumentAdmin(typeDocument, numberDocument, idNomina);
            }
        } else {
            if (!programa.equals("99999")) {
                if (!department.equals("99999")) {
                    transaccionesNominas =
                        transaccionesNominaRepository.findByTypeDocumentAndNumerDocumentUser(
                            typeDocument,
                            numberDocument,
                            department,
                            programa
                        );
                } else {
                    transaccionesNominas =
                        transaccionesNominaRepository.findByTypeDocumentAndNumerDocumentAllDepartmentsUser(
                            typeDocument,
                            numberDocument,
                            programa
                        );
                }
            } else {
                transaccionesNominas = transaccionesNominaRepository.findByTypeDocumentAndNumerDocumentAdmin(typeDocument, numberDocument);
            }
        }

        return transaccionesNominaMapper.transaccionesNominaMap(transaccionesNominas);
    }
}
