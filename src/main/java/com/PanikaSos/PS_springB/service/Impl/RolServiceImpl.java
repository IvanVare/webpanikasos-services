package com.PanikaSos.PS_springB.service.Impl;

import com.PanikaSos.PS_springB.model.ControlUser;
import com.PanikaSos.PS_springB.model.Rol;
import com.PanikaSos.PS_springB.repository.ControlUserRepository;
import com.PanikaSos.PS_springB.repository.RolRepository;
import com.PanikaSos.PS_springB.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RolServiceImpl  implements RolService {
    @Autowired
    private RolRepository rolRepository;

    @Override
    public Rol saveRol(Rol rol){
        rolRepository.save(rol);
        return rol;
    }

    @Override
    public void delelteRol(Long id){
        Rol rol = rolRepository.findById(id).orElse(null);
        if (rol == null){
            return;
        }
        if (rol.getStatus() == 0){
            return;
        }
        rol.setStatus(0);
        rolRepository.save(rol);
    }

    @Override
    public List<Rol> findAllRols(){
        return rolRepository.findAll();
    }


}
