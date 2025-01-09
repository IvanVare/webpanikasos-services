package com.PanikaSos.PS_springB.service.Impl;
import com.PanikaSos.PS_springB.model.ControlUser;
import com.PanikaSos.PS_springB.model.Rol;
import com.PanikaSos.PS_springB.model.User;
import com.PanikaSos.PS_springB.model.dto.ControlUserDTO;
import com.PanikaSos.PS_springB.model.dto.LoginDTO;
import com.PanikaSos.PS_springB.model.dto.UserDTO;
import com.PanikaSos.PS_springB.persistence.dao.UserDAO;
import com.PanikaSos.PS_springB.repository.RolRepository;
import com.PanikaSos.PS_springB.repository.UserRepository;
import com.PanikaSos.PS_springB.service.UserService;
import com.PanikaSos.PS_springB.utils.EncryptPassword;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EncryptPassword encryptPassword;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RolRepository rolRepository;
    

    @Override
    public UserDTO register(User user){
        User user1 = findByEmail(user.getEmail());
        User user2 = findByPhoneNumber(user.getPhoneNumber());
        if (user1 != null){
            throw new RuntimeException("Usuario  ya registrado con el email: " + user.getEmail());
        }
        if (user2 != null){
            throw new RuntimeException("Usuario  ya registrado con el numero: " + user.getPhoneNumber());
        }
        user.setPassword(encryptPassword.encrypt(user.getPassword()));
        userRepository.save(user);
        return new UserDTO(user);
    }

    @Override
    public void deleteUser(Integer id){
        User user = this.userDAO.findById(id).orElse(null);
        if (user == null){
            return;
        }
        if (user.getStatus() == 0){
            return;
        }
        user.setStatus(0);
        userRepository.save(user);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        if (userDAO.isValidate("phoneNumber", userDTO.getPhoneNumber(),userDTO.getId_user())){
            throw new UnsupportedOperationException("Error al guardar el usuario");
        }
        Optional<User> user = this.userDAO.findById(userDTO.getId_user());
        if (user.isPresent()){
            User currentUser = user.get();
            currentUser.setFirst_name(userDTO.getFirst_name());
            currentUser.setLast_name(userDTO.getLast_name());
            currentUser.setPhoneNumber(userDTO.getPhoneNumber());
            currentUser.setAge(userDTO.getAge());
            currentUser.setStatus(userDTO.getStatus());
            this.userRepository.save(currentUser);
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(currentUser, UserDTO.class);
        }else {
            throw new UnsupportedOperationException("Error al guardar el usuario");
        }
    }

    @Override
    public User addUserRol(Integer idUser, Long idRol) {
        User user = this.userDAO.findById(idUser).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Rol rol = rolRepository.findById(idRol).orElseThrow(() -> new EntityNotFoundException("Rol not found"));
        if (user.getStatus() == 0 || rol.getStatus() == 0){
            throw new RuntimeException("Usuario o rol deshabilitado");
        }
        if (user.getUser_rol().contains(rol)){
            throw new RuntimeException("El usuario ya tiene ese rol");
        }
        Set<Rol> rolSet= user.getUser_rol();
        rolSet.add(rol);
        user.setUser_rol(rolSet);
        userRepository.save(user);
        return user;
    }

    @Override
    public Optional<User> findByIdUser(Integer id){
        Optional<User> user = this.userDAO.findById(id);
        if (user.isPresent()){
            return user;
        }else{
        return Optional.of(new User());
        }
    }

    @Override
    public User findByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }

    @Override
    public User findByPhoneNumber(String phoneNumber){
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
        return user.orElse(null);
    }

    @Override
    public List<UserDTO> findAllUsers(){
        ModelMapper modelMapper = new ModelMapper();
        return userDAO.findAll().stream()
                .map(entity -> modelMapper.map(entity,UserDTO.class)).collect(Collectors.toList());
    }


}
