package com.PanikaSos.PS_springB.service.Impl;
import com.PanikaSos.PS_springB.exceptions.RequestExceptions;
import com.PanikaSos.PS_springB.exceptions.ServiceExceptions;
import com.PanikaSos.PS_springB.model.ControlUser;
import com.PanikaSos.PS_springB.model.Rol;
import com.PanikaSos.PS_springB.model.User;
import com.PanikaSos.PS_springB.model.dto.*;
import com.PanikaSos.PS_springB.persistence.dao.UserDAO;
import com.PanikaSos.PS_springB.repository.RolRepository;
import com.PanikaSos.PS_springB.repository.UserRepository;
import com.PanikaSos.PS_springB.service.UserService;
import com.PanikaSos.PS_springB.utils.EncryptPassword;
import com.PanikaSos.PS_springB.utils.jwt.JwtUtils;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
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
    @Autowired
    private JwtUtils jwtUtils;
    

    @Override
    @Transactional
    public UserDTO register(User user) throws RequestExceptions {
        Optional<User> user1 = userRepository.findByEmail(user.getEmail());
        Optional<User> user2 = userRepository.findByPhoneNumber(user.getPhoneNumber());
        if (user1.isPresent()){
            throw new RequestExceptions(HttpStatus.CONFLICT,"The email is already registered");
        }
        if (user2.isPresent()){
            throw new RequestExceptions(HttpStatus.CONFLICT, "The phone number is already registered");
        }
        user.setPassword(encryptPassword.encrypt(user.getPassword()));
        Optional<Rol> rol = rolRepository.findById(2L);
        if (rol.isEmpty()){
            throw new RequestExceptions(HttpStatus.SERVICE_UNAVAILABLE, "The server is not ready to handle the request");
        }
        Set<Rol> rolSet= new HashSet<>();
        rolSet.add(rol.get());
        user.setUser_rol(rolSet);
        userRepository.save(user);
        return new UserDTO(user);
    }

    @Override
    @Transactional
    public void deleteUser(Integer id) throws ServiceExceptions {
        Optional<User> user = Optional.ofNullable(this.userDAO.findById(id).orElse(null));
        if (user.isEmpty()){
            throw new ServiceExceptions(HttpStatus.NOT_FOUND,"The user was not found");
        }
        if (user.get().getStatus() == 0){
            throw new ServiceExceptions(HttpStatus.NOT_FOUND, "The user was not found");
        }
        user.get().setStatus(0);
        userRepository.save(user.get());
    }

    @Override
    @Transactional
    public UserDetailsResponse updateUser(UserDTO userDTO) throws RequestExceptions {
        if (userDAO.isValidate("phoneNumber", userDTO.getPhoneNumber(),userDTO.getId())){
            throw new RequestExceptions(HttpStatus.CONFLICT,"Error saving data");
        }
        Optional<User> user = this.userDAO.findById(userDTO.getId());
        if (user.isPresent()){
            User currentUser = user.get();
            currentUser.setFirstName(userDTO.getFirstName());
            currentUser.setLastName(userDTO.getLastName());
            currentUser.setPhoneNumber(userDTO.getPhoneNumber());
            currentUser.setAge(userDTO.getAge());
            currentUser.setStatus(userDTO.getStatus());
            this.userRepository.save(currentUser);
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(currentUser, UserDetailsResponse.class);
        }else {
            throw new RequestExceptions(HttpStatus.CONFLICT,"Error saving data");
        }
    }

    @Override
    @Transactional
    public User addUserRol(Integer idUser, Long idRol) throws ServiceExceptions{
        Optional<User> user = this.userDAO.findById(idUser);
        Optional<Rol> rol = rolRepository.findById(idRol);
        if (user.isEmpty() || rol.isEmpty()){
            throw new ServiceExceptions(HttpStatus.CONFLICT,"User or role not found");
        }
        if (user.get().getStatus() == 0 || rol.get().getStatus() == 0){
            throw new ServiceExceptions(HttpStatus.CONFLICT,"Invalid user or role");
        }
        if (user.get().getUser_rol().contains(rol.get())){
            throw new ServiceExceptions(HttpStatus.CONFLICT,"The user already has that role");
        }
        Set<Rol> rolSet= user.get().getUser_rol();
        rolSet.add(rol.get());
        user.get().setUser_rol(rolSet);
        userRepository.save(user.get());
        return user.get();
    }



    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByIdUser(Integer id) throws RequestExceptions{
        Optional<User> user = this.userDAO.findById(id);
        if (user.isEmpty()){
            throw new RequestExceptions(HttpStatus.NOT_FOUND,"The user was not found");
        }
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> validateUserByToken(Integer id, String JwtToken) throws RequestExceptions{
        Optional<User> user = this.userDAO.findById(id);
        DecodedJWT decodedJWT = jwtUtils.validateToken(JwtToken);
        String email = jwtUtils.extractEmail(decodedJWT);
        if (user.isPresent() && user.get().getEmail().equals(email)){
            return Optional.of(new UserDTO(user));
        }
        throw new RequestExceptions(HttpStatus.BAD_REQUEST,"The server could not interpret the request");
    }

    //Pendiente
    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) throws RequestExceptions {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByPhoneNumber(String phoneNumber) throws RequestExceptions{
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
        return user.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAllUsers() throws ServiceExceptions{
        ModelMapper modelMapper = new ModelMapper();
        return userDAO.findAll().stream()
                .map(entity -> modelMapper.map(entity,UserDTO.class)).collect(Collectors.toList());
    }


}
