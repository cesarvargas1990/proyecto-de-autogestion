package com.supergiros.portalautogestion.service;

import com.supergiros.portalautogestion.domain.TransaccionesNomina;
import com.supergiros.portalautogestion.repository.DepartamentosRepository;
import com.supergiros.portalautogestion.repository.MunicipioRepository;
import com.supergiros.portalautogestion.repository.ProgramasRepository;
import com.supergiros.portalautogestion.repository.TransaccionesNominaRepository;
import com.supergiros.portalautogestion.repository.UserRepository;
import com.supergiros.portalautogestion.service.dto.TransaccionesNominaListDTO;
import com.supergiros.portalautogestion.service.mapper.transaccionesNominaMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransaccionesNominaService {

    private final Logger log = LoggerFactory.getLogger(TransaccionesNominaService.class);

    @Autowired
    DepartamentosRepository departamentosRepository;

    @Autowired
    MunicipioRepository municipioRepository;

    @Autowired
    ProgramasRepository programasRepository;

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

    public List<TransaccionesNominaListDTO> searchTransacciones(String typeDocument, Long numberDocument, Integer idUser, String idNomina) {
        //List<String> departamentos   = new ArrayList<>();
        List<String> municipios = new ArrayList<>();
        //departamentos = departamentosRepository.findCodDaneUserList(idUser);

        String programa = programasRepository.findNitProgramaUser(idUser);

        municipios = municipioRepository.findCodDaneUserList(idUser);

        List<TransaccionesNomina> transaccionesNominas = new ArrayList<TransaccionesNomina>();
        if (!idNomina.equals("0")) {
            if (!programa.equals("99999")) {
                if (!municipios.contains("99999")) {
                    transaccionesNominas =
                        transaccionesNominaRepository.findByTypeDocumentAndNumerDocumentUser(
                            typeDocument,
                            numberDocument,
                            municipios,
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
                if (!municipios.contains("99999")) {
                    transaccionesNominas =
                        transaccionesNominaRepository.findByTypeDocumentAndNumerDocumentUser(
                            typeDocument,
                            numberDocument,
                            municipios,
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
        try {
            for (int index = 0; index < transaccionesNominas.size(); index++) {
                if (transaccionesNominas.get(index).getFechaVigencia().isBefore(LocalDate.now())) {
                    transaccionesNominas.remove(index);
                }
            }
        } catch (NullPointerException e) {
            log.info("no se encontro el campo fecha de vigencia  para un elemento");
        }

        return transaccionesNominaMapper.transaccionesNominaMap(transaccionesNominas);
    }
}
