package com.supergiros.portalautogestion.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.supergiros.portalautogestion.domain.TransaccionesNomina;
import com.supergiros.portalautogestion.repository.DepartamentosRepository;
import com.supergiros.portalautogestion.repository.MunicipioRepository;
import com.supergiros.portalautogestion.repository.ProgramasRepository;
import com.supergiros.portalautogestion.repository.TransaccionesNominaRepository;
import com.supergiros.portalautogestion.repository.UserRepository;
import com.supergiros.portalautogestion.service.dto.TransaccionesMSDTO;
import com.supergiros.portalautogestion.service.dto.TransaccionesNominaListDTO;
import com.supergiros.portalautogestion.service.mapper.transaccionesNominaMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

@Service
public class TransaccionesNominaService {

    private final Logger logger = LoggerFactory.getLogger(TransaccionesNominaService.class);

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

    @Autowired
    FacadeService facadeService;

    public List<String> findCodDaneDepartamentos(List<Long> departamentosIdLista) {
        List<String> idsLista = new ArrayList<>();
        for (int index = 0; index < departamentosIdLista.size(); index++) {
            idsLista.add(departamentosRepository.getDepartamentosCodDaneById(departamentosIdLista.get(index)));
        }
        return idsLista;
    }

    public List<TransaccionesNominaListDTO> searchTransacciones(String typeDocument, Long numberDocument, Integer idUser, String idNomina) {
        TransaccionesMSDTO transaccionesMSDTO = new TransaccionesMSDTO();
        transaccionesMSDTO.setDocument(numberDocument.toString());
        List<String> programa = programasRepository.findNitProgramaUser(idUser);
        if (!idNomina.equals("0")) {
            transaccionesMSDTO.setIdNomina(idNomina);
        }
        if (programa.get(0).equals("99999")) {
            transaccionesMSDTO.setNIT(new ArrayList<>());
            transaccionesMSDTO.setMunicipalities(new ArrayList<>());
        } else if (!programa.get(0).equals("900490473")) {
            if (programa.get(0).equals("9000395338")) {
                programa.add("900039533");
            }
            transaccionesMSDTO.setNIT(programa);

            List<String> municipalities = municipioRepository.findCodDaneUserList(idUser);
            if (!municipalities.get(0).equals("99999")) {
                transaccionesMSDTO.setMunicipalities(municipalities);
            } else {
                transaccionesMSDTO.setMunicipalities(new ArrayList<>());
            }
        } else if (programa.get(0).equals("900490473")) {
            programa.add("9004904731");
            programa.add("9004904732");
            transaccionesMSDTO.setNIT(programa);
            transaccionesMSDTO.setMunicipalities(new ArrayList<>());
        }

        List<TransaccionesNomina> transaccionesNominas = new ArrayList<TransaccionesNomina>();

        try {
            transaccionesNominas = facadeService.getTransacciones(transaccionesMSDTO);
        } catch (InterruptedException e) {
            logger.error("No fue posible conectarse con el servicio de consulta");
        } catch (ResourceAccessException e) {
            logger.error("No fue posible conectarse con el servicio de consulta");
        } catch (JsonProcessingException e) {
            logger.error("No fue posible procesar el objeto json de la consulta");
        }
        List<TransaccionesNomina> transaccionesNominasHist = searchExternal(
            typeDocument,
            numberDocument,
            programa.get(0),
            municipioRepository.findCodDaneUserList(idUser),
            idNomina
        );
        List<TransaccionesNomina> transaccionesNominasGlobal = Stream
            .concat(transaccionesNominas.stream(), transaccionesNominasHist.stream())
            .collect(Collectors.toList());

        return transaccionesNominaMapper.transaccionesNominaMap(transaccionesNominasGlobal);
        //return null;
    }

    List<TransaccionesNomina> searchExternal(
        String typeDocument,
        Long numberDocument,
        String programa,
        List<String> municipios,
        String idNomina
    ) {
        List<TransaccionesNomina> transaccionesNominasHist = new ArrayList<TransaccionesNomina>();
        if (!idNomina.equals("0")) {
            if (!programa.equals("99999")) {
                if (!municipios.contains("99999")) {
                    transaccionesNominasHist =
                        transaccionesNominaRepository.findByTypeDocumentAndNumerDocumentUser(
                            typeDocument,
                            numberDocument,
                            municipios,
                            programa,
                            idNomina
                        );
                } else {
                    transaccionesNominasHist =
                        transaccionesNominaRepository.findByTypeDocumentAndNumerDocumentAllDepartmentsUser(
                            typeDocument,
                            numberDocument,
                            programa,
                            idNomina
                        );
                }
            } else {
                transaccionesNominasHist =
                    transaccionesNominaRepository.findByTypeDocumentAndNumerDocumentAdmin(typeDocument, numberDocument, idNomina);
            }
        } else {
            if (!programa.equals("99999")) {
                if (!municipios.contains("99999")) {
                    transaccionesNominasHist =
                        transaccionesNominaRepository.findByTypeDocumentAndNumerDocumentUser(
                            typeDocument,
                            numberDocument,
                            municipios,
                            programa
                        );
                } else {
                    transaccionesNominasHist =
                        transaccionesNominaRepository.findByTypeDocumentAndNumerDocumentAllDepartmentsUser(
                            typeDocument,
                            numberDocument,
                            programa
                        );
                }
            } else {
                transaccionesNominasHist =
                    transaccionesNominaRepository.findByTypeDocumentAndNumerDocumentAdmin(typeDocument, numberDocument);
            }
        }
        return transaccionesNominasHist;
    }
}
