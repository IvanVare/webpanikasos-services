package com.PanikaSos.PS_springB.service;

import com.PanikaSos.PS_springB.model.ControlUser;
import com.PanikaSos.PS_springB.model.Rol;
import com.PanikaSos.PS_springB.model.dto.ControlUserDTO;

import java.util.List;
import java.util.Set;

public interface ControlUserService {
    ControlUser saveControlUser(ControlUser controlUser);

    void deleteControlUser(Long id);

    ControlUserDTO updateControlUser(ControlUserDTO controlUserDTO);

    List<ControlUserDTO> findAllControlUser();

    ControlUser findByEmail(String email);

    ControlUserDTO findByIdControlUser(Long idUser);

    ControlUser addUserRol(Long idUser, Long idRol);

    Set<Rol> findRolesByIdUser(Long idUser);
}
