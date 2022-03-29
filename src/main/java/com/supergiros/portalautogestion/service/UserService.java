package com.supergiros.portalautogestion.service;

import com.supergiros.portalautogestion.config.Constants;
import com.supergiros.portalautogestion.domain.Authority;
import com.supergiros.portalautogestion.domain.User;
import com.supergiros.portalautogestion.repository.AuthorityRepository;
import com.supergiros.portalautogestion.repository.DepartamentosRepository;
import com.supergiros.portalautogestion.repository.UserDepartamentoMunicipioRepository;
import com.supergiros.portalautogestion.repository.UserRepository;
import com.supergiros.portalautogestion.security.AuthoritiesConstants;
import com.supergiros.portalautogestion.security.SecurityUtils;
import com.supergiros.portalautogestion.service.dto.AdminUserDTO;
import com.supergiros.portalautogestion.service.dto.UserDTO;
import com.supergiros.portalautogestion.service.dto.UserDepartamentoMunicipioDTO;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.security.RandomUtil;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    long numeroId;

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final DepartamentosRepository departamentosRepository;

    private final UserDepartamentoMunicipioRepository userDepartamentoMunicipioRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    private final CacheManager cacheManager;

    public UserService(
        UserRepository userRepository,
        DepartamentosRepository departamentosRepository,
        PasswordEncoder passwordEncoder,
        UserDepartamentoMunicipioRepository userDepartamentoMunicipioRepository,
        AuthorityRepository authorityRepository,
        CacheManager cacheManager
    ) {
        this.userRepository = userRepository;
        this.departamentosRepository = departamentosRepository;
        this.userDepartamentoMunicipioRepository = userDepartamentoMunicipioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.cacheManager = cacheManager;
    }

    public List<String> getDepartamentosName() {
        return departamentosRepository.getDepartamentosName();
    }

    public List<Long> findIdByMultiplesName(String[] departamentoName) {
        List<Long> Departamentos = new ArrayList();
        for (int index = 0; index < departamentoName.length; index++) {
            Departamentos.add(departamentosRepository.findIdByName(departamentoName[index]));
        }
        return Departamentos;
    }

    public Long findIdByMultipleName(String departamentoName) {
        Long Departamento;
        Departamento = (departamentosRepository.findIdByName(departamentoName));

        return Departamento;
    }

    public Long findIdByNameMunicipio(String municipioName) {
        return departamentosRepository.findIdByNameMunicipio(municipioName);
    }

    public String findDepartamentosIDByMunicipioName(String municipioName) {
        String departamentoId = (departamentosRepository.findDepartamentosIDByMunicipioName(municipioName)).toString();
        return departamentoId;
    }

    public void userDMInsert(UserDepartamentoMunicipioDTO udmDTO) {
        long userId = udmDTO.getuserId();
        String[] departamentoName = udmDTO.getDepartamentoName();
        String[] municipioName = udmDTO.getmunicipioName();
        if (userDepartamentoMunicipioRepository.hasALocation(userId) != null) {
            userDepartamentoMunicipioRepository.deleteCurrentLocation(userId);
        }

        for (int i = 0; i < municipioName.length; i++) {
            userDepartamentoMunicipioRepository.userDMInsert(userId, departamentoName[i], municipioName[i]);
        }
    }

    public List<String> getConvenio() {
        return departamentosRepository.getConvenioName();
    }

    public Long findIdByNameConvenio(String convenioName) {
        Long a = departamentosRepository.findIdByNameConvenio(convenioName);
        return a;
    }

    public List<String> getProgramasName(Long fkPrograma) {
        return departamentosRepository.getProgramaName(fkPrograma);
    }

    public Long findIdByNamePrograma(String programaName) {
        return departamentosRepository.findIdByNamePrograma(programaName);
    }

    public List<String> findDepartamentosNameByID(Long IdUser) {
        return departamentosRepository.findDepartamentosNameByID(IdUser);
    }

    public List<String> findMunicipiosNameByID(Long IdUser) {
        return departamentosRepository.findMunicipiosNameByID(IdUser);
    }

    public String findProgramaName(Long IdUser) {
        return (departamentosRepository.findProgramaName(IdUser)).toString();
    }

    //Intento2
    public Long findProgramasName(Long IdUser, String programaName) {
        return departamentosRepository.findProgramasName(IdUser, programaName);
    }

    public Long findConvenioID(Long IdUser) {
        return departamentosRepository.findConvenioID(IdUser);
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository
            .findOneByActivationKey(key)
            .map(user -> {
                // activate given user for the registration key.
                user.setActivated(true);
                user.setActivationKey(null);
                this.clearUserCaches(user);
                log.debug("Activated user: {}", user);
                return user;
            });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        Optional<User> userprueba = userRepository.findOneByResetKey(key);
        if (userprueba.get().getFirstTime()) {
            return userRepository
                .findOneByResetKey(key)
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    user.setResetKey(null);
                    user.setResetDate(null);
                    user.setFirstTime(false);
                    this.clearUserCaches(user);
                    return user;
                });
        } else {
            if (!userprueba.get().getResetDate().isAfter(Instant.now().minus(Constants.TOKEN_DURATION, ChronoUnit.MINUTES))) {
                throw new BadCredentialsException("El Código suministrado ha caducado");
            }
            return userRepository
                .findOneByResetKey(key)
                .filter(user -> user.getResetDate().isAfter(Instant.now().minus(Constants.TOKEN_DURATION, ChronoUnit.MINUTES)))
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    user.setResetKey(null);
                    user.setResetDate(null);
                    this.clearUserCaches(user);
                    return user;
                });
        }
    }

    public Optional<User> requestPasswordReset(String document) {
        System.out.println(Instant.now());
        return userRepository
            .findOneByLogin(document)
            .filter(User::isActivated)
            .map(user -> {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(Instant.now());
                this.clearUserCaches(user);
                return user;
            });
    }

    public User registerUser(AdminUserDTO userDTO, String password) {
        userRepository
            .findOneByLogin(userDTO.getLogin().toLowerCase())
            .ifPresent(existingUser -> {
                boolean removed = removeNonActivatedUser(existingUser);
                if (!removed) {
                    throw new UsernameAlreadyUsedException();
                }
            });
        userRepository
            .findOneByEmailIgnoreCase(userDTO.getEmail())
            .ifPresent(existingUser -> {
                boolean removed = removeNonActivatedUser(existingUser);
                if (!removed) {
                    throw new EmailAlreadyUsedException();
                }
            });
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(userDTO.getLogin().toLowerCase());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        //newUser.setDepartamentos(userDTO.getDepartamentos());
        if (userDTO.getEmail() != null) {
            newUser.setEmail(userDTO.getEmail().toLowerCase());
        }
        newUser.setImageUrl(userDTO.getImageUrl());
        newUser.setLangKey(userDTO.getLangKey());
        // new user is not active
        newUser.setActivated(userDTO.isActivated());
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        this.clearUserCaches(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    private boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.isActivated()) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        this.clearUserCaches(existingUser);
        return true;
    }

    public User createUser(AdminUserDTO userDTO) {
        User user = new User();
        user.setConvenio(userDTO.getConvenio());
        user.setPrograma(userDTO.getPrograma());
        user.setDepartamento(userDTO.getDepartamento());
        //user.setDepartamentos(userDTO.getDepartamentos());
        user.setMunicipio(userDTO.getMunicipio());
        user.setDocumentType(userDTO.getDocumentType());
        user.setCelphone(userDTO.getCelphone());

        user.setLogin(userDTO.getLogin().toLowerCase());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail().toLowerCase());
        }
        user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(userDTO.isActivated());
        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = userDTO
                .getAuthorities()
                .stream()
                .map(authorityRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
            user.setAuthorities(authorities);
        }
        userRepository.save(user);
        this.clearUserCaches(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update.
     * @return updated user.
     */
    public Optional<AdminUserDTO> updateUser(AdminUserDTO userDTO) {
        return Optional
            .of(userRepository.findById(userDTO.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(user -> {
                this.clearUserCaches(user);
                user.setLogin(userDTO.getLogin().toLowerCase());
                user.setConvenio(userDTO.getConvenio());
                user.setPrograma(userDTO.getPrograma());
                user.setDepartamento(userDTO.getDepartamento());
                // user.setDepartamentos(userDTO.getDepartamentos());
                user.setMunicipio(userDTO.getMunicipio());
                user.setDocumentType(userDTO.getDocumentType());
                user.setCelphone(userDTO.getCelphone());

                user.setFirstName(userDTO.getFirstName());
                user.setLastName(userDTO.getLastName());
                if (userDTO.getEmail() != null) {
                    user.setEmail(userDTO.getEmail().toLowerCase());
                }
                user.setImageUrl(userDTO.getImageUrl());
                user.setActivated(userDTO.isActivated());
                user.setLangKey(userDTO.getLangKey());
                Set<Authority> managedAuthorities = user.getAuthorities();
                managedAuthorities.clear();
                userDTO
                    .getAuthorities()
                    .stream()
                    .map(authorityRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .forEach(managedAuthorities::add);
                this.clearUserCaches(user);
                log.debug("Changed Information for User: {}", user);
                return user;
            })
            .map(AdminUserDTO::new);
    }

    public void deleteUser(String login) {
        userRepository
            .findOneByLogin(login)
            .ifPresent(user -> {
                userRepository.delete(user);
                this.clearUserCaches(user);
                log.debug("Deleted User: {}", user);
            });
    }

    /**
     * Update basic information (first name, last name, email, language) for the
     * current user.
     *
     * @param firstName first name of user.
     * @param lastName  last name of user.
     * @param email     email id of user.
     * @param langKey   language key.
     * @param imageUrl  image URL of user.
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                user.setFirstName(firstName);
                user.setLastName(lastName);
                if (email != null) {
                    user.setEmail(email.toLowerCase());
                }
                user.setLangKey(langKey);
                user.setImageUrl(imageUrl);
                this.clearUserCaches(user);
                log.debug("Changed Information for User: {}", user);
            });
    }

    @Transactional
    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                String currentEncryptedPassword = user.getPassword();
                if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                    throw new InvalidPasswordException();
                }
                String encryptedPassword = passwordEncoder.encode(newPassword);
                user.setPassword(encryptedPassword);
                this.clearUserCaches(user);
                log.debug("Changed password for User: {}", user);
            });
    }

    @Transactional(readOnly = true)
    public Page<AdminUserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(AdminUserDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllPublicUsers(Pageable pageable) {
        return userRepository.findAllByIdNotNullAndActivatedIsTrue(pageable).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
    }

    @Transactional(readOnly = true)
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        userRepository
            .findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
            .forEach(user -> {
                log.debug("Deleting not activated user {}", user.getLogin());
                userRepository.delete(user);
                this.clearUserCaches(user);
            });
    }

    /**
     * Gets a list of all the authorities.
     *
     * @return a list of all the authorities.
     */

    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        if (user.getEmail() != null) {
            Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
        }
    }

    public boolean userStatus(String login) {
        Optional<User> user = userRepository.findOneByLogin(login);
        return user.get().isActivated();
    }

    public void userFirstLogin(String login) {
        Optional<User> user = userRepository.findOneByLogin(login);
        if (user.get().getFirstTime()) {
            userRepository.setUserLogeado(login);
        } else {
            log.info("el usuario ya había logeado antes");
        }
    }

    public Optional<User> tokenAuthentication(String key) {
        log.debug("Reset user password for reset key {}", key);
        Optional<User> userprueba = userRepository.findOneByResetKey(key);
        if (!userprueba.get().getResetDate().isAfter(Instant.now().minus(Constants.TOKEN_DURATION, ChronoUnit.MINUTES))) {
            throw new BadCredentialsException("El Código suministrado ha caducado");
        }
        return userRepository
            .findOneByResetKey(key)
            .filter(user -> user.getResetDate().isAfter(Instant.now().minus(Constants.TOKEN_DURATION, ChronoUnit.MINUTES)))
            .map(user -> {
                user.setResetKey(null);
                user.setResetDate(null);
                this.clearUserCaches(user);
                return user;
            });
    }

    public void setJWT(String jwt, String login) {
        userRepository.setJWT(jwt, login);
    }

    public Long findIdByName(String departamentoName) {
        return departamentosRepository.findIdByName(departamentoName);
    }

    public List<String> getMunicipiosName(Long fkDepartmanento) {
        return departamentosRepository.getMunicipiosName(fkDepartmanento);
    }

    public List<String> getMunicipiosNames(Long[] fkDepartmanento) {
        List<String> Municipios = new ArrayList();
        for (int index = 0; index < fkDepartmanento.length; index++) {
            Municipios.addAll(departamentosRepository.getMunicipiosName(fkDepartmanento[index]));
        }
        return Municipios;
    }

    //DUDOSOS

    public Long findDepartamentosIDByMunicipiosName(String municipioName) {
        return departamentosRepository.findDepartamentosIDByMunicipiosName(municipioName);
    }

    public List<String> findMultiplesDepartamentosIDByMunicipiosName(String[] municipioName) {
        List<String> Departamentos = new ArrayList();
        for (int index = 0; index < municipioName.length; index++) {
            Departamentos.addAll((departamentosRepository.findMultiplesDepartamentosIDByMunicipiosName(municipioName[index])));
            System.out.print(
                "AQUIIIIIIIIIIIIIII Departamentos completo: " + Departamentos + " Departamento[i]: " + Departamentos.get(index)
            );
        }
        return Departamentos;
    }

    public List<String> findNameByIdConvenio(Long convenioId) {
        return departamentosRepository.findNameByIdConvenio(convenioId);
    }

    public List<String> findNameByIdPrograma(Long programaId) {
        return departamentosRepository.findNameByIdPrograma(programaId);
    }

    public List<String> findNameByIdDepartamento(Long departamentoId) {
        return departamentosRepository.findNameByIdDepartamento(departamentoId);
    }

    public List<String> findNameByIdMunicipio(Long municipioId) {
        return departamentosRepository.findNameByIdMunicipio(municipioId);
    }

    // public List<Long> findIdsDepartamentos(List <String> departamentosLista){
    // List<Long> idsLista= Collections.emptyList();
    // for (int index = 0; index < departamentosLista.length; index++) {
    // Optional<Departamentos> departamento =
    // departamentosRepository.findListbyId(string);
    // idsLista.add(departamento.get().getId());
    // }
    // return idsLista;
    // }

    public List<Long> findIdsDepartamentos(List<String> departamentosLista) {
        System.out.println("inicio");
        List<Long> idsLista = new ArrayList<>();
        System.out.println("despues de la list");
        for (int index = 0; index < departamentosLista.size(); index++) {
            numeroId = departamentosRepository.findIdByName(departamentosLista.get(index));

            idsLista.add(index, numeroId);
        }

        return idsLista;
    }

    public Boolean searchInDB(String login, String documentType) {
        return departamentosRepository.searchInDB(login, documentType);
    }
}
