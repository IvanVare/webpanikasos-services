package com.PanikaSos.PS_springB.service;

import com.PanikaSos.PS_springB.model.Rol;

import java.util.List;

public interface RolService {
    Rol saveRol(Rol rol);

    void delelteRol(Long id);

    List<Rol> findAllRols();


}
