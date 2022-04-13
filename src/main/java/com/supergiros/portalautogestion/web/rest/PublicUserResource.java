package com.supergiros.portalautogestion.web.rest;

import com.supergiros.portalautogestion.service.UserService;
import com.supergiros.portalautogestion.service.dto.UserDTO;
import java.util.*;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

@RestController
@RequestMapping("/api")
public class PublicUserResource {

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
        Arrays.asList("id", "login", "firstName", "lastName", "email", "activated", "langKey")
    );

    private final Logger log = LoggerFactory.getLogger(PublicUserResource.class);

    private final UserService userService;

    public PublicUserResource(UserService userService) {
        this.userService = userService;
    }

    /**
     * {@code GET /users} : get all users with only the public informations -
     * calling this are allowed for anyone.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         all users.
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllPublicUsers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get all public User names");
        if (!onlyContainsAllowedProperties(pageable)) {
            return ResponseEntity.badRequest().build();
        }

        final Page<UserDTO> page = userService.getAllPublicUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }

    /**
     * Gets a list of all roles.
     *
     * @return a string list of all roles.
     */
    @GetMapping("/authorities")
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }

    /**
     * Gets a list of all departamentos.
     *
     * @return a string list of all roles.
     */
    @GetMapping("/getDepartamentos")
    public List<String> getDepartamentos() {
        return userService.getDepartamentosName();
    }

    /**
     * Gets the id of Departamento using its name.
     *
     * @return a string list of all roles.
     */
    @GetMapping("/findIdByMultiplesName")
    public List<Long> findIdByMultiplesName(String[] departamentoName) {
        return userService.findIdByMultiplesName(departamentoName);
    }

    /**
     * Gets the id of Departamento using its name.
     *
     * @return a string list of all roles.
     */
    @GetMapping("/findIdByMultipleName")
    public Long findIdByMultipleName(String departamentoName) {
        return userService.findIdByMultipleName(departamentoName);
    }

    /**
     * Gets the id of Departamento using its name.
     *
     * @return a string list of all roles.
     */
    @GetMapping("/findDepartamentosIDByMunicipioName")
    public String findDepartamentosIDByMunicipioName(String municipioName) {
        return userService.findDepartamentosIDByMunicipioName(municipioName);
    }

    /**
     * Gets the id of Municipio using its name.
     *
     * @return a string list of all roles.
     */
    @GetMapping("/getIdMunicipios")
    public Long getIdMunicipios(String municipioName) {
        return userService.findIdByNameMunicipio(municipioName);
    }

    /**
     * Gets a list of all convenios.
     *
     * @return a string list of all roles.
     */
    @GetMapping("/getConvenio")
    public List<String> getConvenio() {
        return userService.getConvenio();
    }

    /**
     * Gets the id of convenio using its name.
     *
     * @return a string list of all roles.
     */
    @GetMapping("/getIdConvenio")
    public Long getIdConvenio(String convenioName) {
        System.out.println("AHHHH ESTE LLEGA" + convenioName);
        return userService.findIdByNameConvenio(convenioName);
    }

    /**
     * Gets a list of all programas.
     *
     * @return a string list of all roles.
     */
    @GetMapping("/getProgramas")
    public List<String> getProgramas(Long fkConvenio) {
        return userService.getProgramasName(fkConvenio);
    }

    /**
     * Gets the id of programa using its name.
     *
     * @return a string list of all roles.
     */
    @GetMapping("/getIdProgramas")
    public Long getIdProgramas(String programaName) {
        return userService.findIdByNamePrograma(programaName);
    }

    @GetMapping("/findDepartamentosNameByID")
    public List<String> findDepartamentosNameByID(Long IdUser) {
        return userService.findDepartamentosNameByID(IdUser);
    }

    @GetMapping("/findMunicipiosNameByID")
    public List<String> findMunicipiosNameByID(Long IdUser) {
        return userService.findMunicipiosNameByID(IdUser);
    }

    @GetMapping("/findMunicipiosNameByIDAndDepartamento")
    public List<String> findMunicipiosNameByIDAndDepartamento(Long IdUser, Long[] departamentoId) {
        return userService.findMunicipiosNameByIDAndDepartamento(IdUser, departamentoId);
    }

    // /**
    //  * Gets a list of all municipios.
    //  *
    //  * @return a string list of all roles.
    //  */
    // @GetMapping("/getMunicipios")
    // public List<String> getMunicipios(Long fkDepartmanento) {
    //     return userService.getMunicipiosName(fkDepartmanento);
    // }

    /**
     * Gets a list of all municipios.
     *
     * @return a string list of all roles.
     */
    @GetMapping("/getMultiplesMunicipios")
    public List<String> getMunicipios(Long[] fkDepartmanento) {
        return userService.getMunicipiosNames(fkDepartmanento);
    }

    /**
     * Gets a list of all municipios.
     *
     * @return a string list of all roles.
     */
    @GetMapping("/findProgramaName")
    public String findProgramaName(Long IdUser) {
        return userService.findProgramaName(IdUser);
    }

    /**
     * Gets a list of all municipios.
     *
     * @return a string list of all roles.
     */
    @GetMapping("/findProgramasName")
    public Long findProgramasName(Long IdUser, String programaName) {
        return userService.findProgramasName(IdUser, programaName);
    }

    /**
     * Gets a list of all municipios.
     *
     * @return a string list of all roles.
     */
    @GetMapping("/findConvenioID")
    public Long findConvenioID(Long IdUser) {
        return userService.findConvenioID(IdUser);
    }

    //DUDOSOS
    /**
     * Gets a boolean confirmation if document type and document number exist in DB.
     *
     * @return a string list of all roles.
     */
    @GetMapping("/searchInDB")
    public Boolean searchInDB(String login, String documentType) {
        return userService.searchInDB(login, documentType);
    }

    // @GetMapping("/getDepartamentosId")
    // public List<Long> getDepartamentoId(@RequestBody List<String> )

    /**
     * Gets the id of programa using its name.
     *
     * @return a string list of all roles.
     */
    @GetMapping("/findNameByIdConvenio")
    public List<String> findNameByIdConvenio(Long convenioId) {
        return userService.findNameByIdConvenio(convenioId);
    }

    /**
     * Gets the id of programa using its name.
     *
     * @return a string list of all roles.
     */
    @GetMapping("/findNameByIdPrograma")
    public List<String> findNameByIdPrograma(Long programaId) {
        return userService.findNameByIdPrograma(programaId);
    }

    /**
     * Gets the id of programa using its name.
     *
     * @return a string list of all roles.
     */
    @GetMapping("/findNameByIdDepartamento")
    public List<String> findNameByIdDepartamento(Long departamentoId) {
        return userService.findNameByIdDepartamento(departamentoId);
    }

    /**
     * Gets the id of programa using its name.
     *
     * @return a string list of all roles.
     */
    @GetMapping("/findNameByIdMunicipio")
    public List<String> findNameByIdMunicipio(Long municipioId) {
        return userService.findNameByIdMunicipio(municipioId);
    }

    /**
     * Gets the id of Departamento using its name.
     *
     * @return a string list of all roles.
     */
    @GetMapping("/getIdDepartamentos")
    public Long getIdDepartamentos(String departamentoName) {
        return userService.findIdByName(departamentoName);
    }

    /**
     * Gets the id of Departamento using its name.
     *
     * @return a string list of all roles.
     */
    @GetMapping("/getDepartamentosIDByMunicipiosName")
    public Long getDepartamentosIDByMunicipiosName(String municipioName) {
        return userService.findDepartamentosIDByMunicipiosName(municipioName);
    }

    /**
     * Gets the id of Departamento using its name.
     *
     * @return a string list of all roles.
     */
    @GetMapping("/findMultiplesDepartamentosIDByMunicipiosName")
    public List<String> findMultiplesDepartamentosIDByMunicipiosName(String[] municipioName) {
        return userService.findMultiplesDepartamentosIDByMunicipiosName(municipioName);
    }
}
