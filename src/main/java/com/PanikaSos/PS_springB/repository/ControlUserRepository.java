package com.PanikaSos.PS_springB.repository;

import com.PanikaSos.PS_springB.model.ControlUser;
import com.PanikaSos.PS_springB.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ControlUserRepository extends JpaRepository<ControlUser, Long> {
    Optional<ControlUser> findByEmail(String email);

    @Query("SELECT u FROM ControlUser u JOIN FETCH u.cuser_rol r WHERE u.id = :idUser")
    ControlUser findByIdWithRoles(@Param("idUser") Long idUser);


}
