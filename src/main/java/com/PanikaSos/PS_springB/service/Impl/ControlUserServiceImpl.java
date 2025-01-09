package com.PanikaSos.PS_springB.service.Impl;

import com.PanikaSos.PS_springB.model.ControlUser;
import com.PanikaSos.PS_springB.model.Rol;
import com.PanikaSos.PS_springB.model.dto.ControlUserDTO;
import com.PanikaSos.PS_springB.persistence.dao.ControlUserDAO;
import com.PanikaSos.PS_springB.repository.ControlUserRepository;
import com.PanikaSos.PS_springB.repository.RolRepository;
import com.PanikaSos.PS_springB.service.ControlUserService;
import com.PanikaSos.PS_springB.utils.EncryptPassword;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ControlUserServiceImpl implements ControlUserService {

    @Autowired
    private ControlUserRepository controlUserRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private EncryptPassword encryptPassword;
    @Autowired
    private ControlUserDAO controlUserDAO;

    @Override
    public ControlUser saveControlUser(ControlUser controlUser){
        ControlUser controlUser1 = findByEmail(controlUser.getEmail());
        if (controlUser1 != null){
            throw new RuntimeException("Usuario  ya registrado con el email: " + controlUser1.getEmail());
        }
        controlUser.setPassword(encryptPassword.encrypt(controlUser.getPassword()));
        controlUserRepository.save(controlUser);
        return controlUser;
    }

    @Override
    public void deleteControlUser(Long id){
        ControlUser controlUser = controlUserRepository.findById(id).orElse(null);
        if (controlUser == null){
            return;
        }
        if (controlUser.getStatus() == 0){
            return;
        }
        controlUser.setStatus(0);
        controlUserRepository.save(controlUser);
    }

    @Override
    public ControlUserDTO updateControlUser(ControlUserDTO controlUserDTO) {
        Optional<ControlUser> controlUser = this.controlUserDAO.findById(controlUserDTO.getId());
        if (controlUser.isPresent()){
            ControlUser currentControlUser = controlUser.get();
            currentControlUser.setFirstName(controlUserDTO.getFirstName());
            currentControlUser.setLastName(controlUserDTO.getLastName());
            currentControlUser.setEmail(controlUserDTO.getEmail());
            currentControlUser.setStatus(controlUserDTO.getStatus());
            this.controlUserRepository.save(currentControlUser);
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(currentControlUser, ControlUserDTO.class);
        }else {
            throw new UnsupportedOperationException("Error al guardar el usuario");
        }
    }

    @Override
    public List<ControlUserDTO> findAllControlUser(){
        ModelMapper modelMapper = new ModelMapper();

        return this.controlUserDAO.findAll().stream()
                .map(entity -> modelMapper.map(entity, ControlUserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ControlUser findByEmail(String email){
        Optional<ControlUser> controlUser= controlUserRepository.findByEmail(email);
        return controlUser.orElse(null);
    }

    @Override
    public ControlUserDTO findByIdControlUser(Long idUser){
        Optional<ControlUser> controlUser= this.controlUserDAO.findById(idUser);
        if (controlUser.isPresent()){
            ModelMapper modelMapper = new ModelMapper();
            ControlUser currentUser = controlUser.get();
            return modelMapper.map(currentUser, ControlUserDTO.class);
        }else {
            return new ControlUserDTO();
        }

    }

    @Override
    public ControlUser addUserRol(Long idUser, Long idRol) {
        ControlUser controlUser = controlUserRepository.findById(idUser).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Rol rol = rolRepository.findById(idRol).orElseThrow(() -> new EntityNotFoundException("Rol not found"));
        if (controlUser.getStatus() == 0 || rol.getStatus() == 0){
            throw new RuntimeException("Usuario o rol deshabilitado");
        }
        if (controlUser.getCuser_rol().contains(rol)){
            throw new RuntimeException("El usuario ya tiene ese rol");
        }
        Set<Rol> rolSet= controlUser.getCuser_rol();
        rolSet.add(rol);
        controlUser.setCuser_rol(rolSet);
        controlUserRepository.save(controlUser);
        return controlUser;
    }

    @Override
    public Set<Rol> findRolesByIdUser(Long idUser) {
        ControlUser controlUser = controlUserRepository.findByIdWithRoles(idUser);
        return controlUser.getCuser_rol();
    }


}
