package com.supergiros.portalautogestion.service;

import com.supergiros.portalautogestion.repository.DepartamentosRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransaccionesNominaService {

    @Autowired
    DepartamentosRepository departamentosRepository;

    public List<String> findCodDaneDepartamentos(List<Long> departamentosIdLista) {
        List<String> idsLista = new ArrayList<>();
        for (int index = 0; index < departamentosIdLista.size(); index++) {
            idsLista.add(departamentosRepository.getDepartamentosCodDaneById(departamentosIdLista.get(index)));
        }
        System.out.println("despues del for ");
        return idsLista;
    }
}
