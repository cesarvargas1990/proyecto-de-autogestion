package com.supergiros.portalautogestion.repository;

import com.supergiros.portalautogestion.domain.UserDepartamentoMunicipio;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDepartamentoMunicipioRepository extends JpaRepository<UserDepartamentoMunicipio, Long> {
    @Modifying
    @Query(value = "INSERT INTO jhi_user_location(user_id, departamento_name,  municipio_name)VALUES (?, ?, ?) ", nativeQuery = true)
    void userDMInsert(long userId, String departamentoName, String municipioName);
}
