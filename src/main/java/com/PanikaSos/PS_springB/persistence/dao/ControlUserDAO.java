package com.PanikaSos.PS_springB.persistence.dao;

import com.PanikaSos.PS_springB.model.ControlUser;
import com.PanikaSos.PS_springB.model.dto.ControlUserDTO;

import java.util.List;
import java.util.Optional;

public interface ControlUserDAO {
    List<ControlUser> findAll();
    Optional<ControlUser> findById(Long id);
}
