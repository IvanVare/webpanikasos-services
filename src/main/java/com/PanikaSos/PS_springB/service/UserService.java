package com.PanikaSos.PS_springB.service;

import com.PanikaSos.PS_springB.exceptions.ServiceExceptions;
import com.PanikaSos.PS_springB.model.User;
import com.PanikaSos.PS_springB.model.dto.AuthResponse;
import com.PanikaSos.PS_springB.model.dto.LoginDTO;
import com.PanikaSos.PS_springB.model.dto.UserDTO;
import com.PanikaSos.PS_springB.model.dto.UserDetailsResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {


    UserDTO register(User user);

    void deleteUser(Integer id) throws ServiceExceptions;

    UserDetailsResponse updateUser(UserDTO userDTO);

    User addUserRol(Integer idUser, Long idRol) throws ServiceExceptions;

    Optional<User> findByIdUser(Integer id);

    Optional<UserDTO> validateUserByToken(Integer id,String JwtToken);

    User findByEmail(String Email);

    User findByPhoneNumber(String phone_number);

    List<UserDTO> findAllUsers() throws ServiceExceptions;


}
