package com.PanikaSos.PS_springB.repository;

import com.PanikaSos.PS_springB.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol,Long> {
}
