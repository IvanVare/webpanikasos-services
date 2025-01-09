package com.PanikaSos.PS_springB.service;

import com.PanikaSos.PS_springB.model.User;
import com.PanikaSos.PS_springB.model.dto.LoginDTO;
import com.PanikaSos.PS_springB.model.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {


    UserDTO register(User user);

    void deleteUser(Integer id);

    UserDTO updateUser(UserDTO userDTO);

    User addUserRol(Integer idUser, Long idRol);

    Optional<User> findByIdUser(Integer id);

    User findByEmail(String Email);

    User findByPhoneNumber(String phone_number);

    List<UserDTO> findAllUsers();


}
