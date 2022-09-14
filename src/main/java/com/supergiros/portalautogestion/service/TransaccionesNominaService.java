package com.supergiros.portalautogestion.service;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        //        try {
        //            for (int index = 0; index < transaccionesNominas.size(); index++) {
        //                if (transaccionesNominas.get(index).getFechaVigencia().isBefore(LocalDate.now())) {
        //                    transaccionesNominas.remove(index);
        //                }
        //            }
        //        } catch (NullPointerException e) {
        //            log.info("no se encontro el campo fecha de vigencia  para un elemento");
        //        }
        try {
            transaccionesNominas = facadeService.getTransacciones(transaccionesMSDTO);
        } catch (InterruptedException e) {
            logger.error("No fue posible conectarse con el servicio de consulta");
            return null;
        }

        System.out.println(transaccionesNominas);
        return transaccionesNominaMapper.transaccionesNominaMap(transaccionesNominas);
        //return null;
    }
}
