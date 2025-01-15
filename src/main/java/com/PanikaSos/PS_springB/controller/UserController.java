package com.PanikaSos.PS_springB.controller;

import com.PanikaSos.PS_springB.model.User;
import com.PanikaSos.PS_springB.model.dto.AuthResponse;
import com.PanikaSos.PS_springB.model.dto.UserDTO;
import com.PanikaSos.PS_springB.model.dto.UserDetailsResponse;
import com.PanikaSos.PS_springB.service.UserService;
import com.PanikaSos.PS_springB.utils.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("denyAll()")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> register(@RequestBody User user){
        return new ResponseEntity<>(userService.register(user), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
        return new ResponseEntity<>("Usuario borrado",HttpStatus.OK);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<UserDetailsResponse> updateUser(@RequestBody UserDTO userDTO){
        return new ResponseEntity<>(userService.updateUser(userDTO), HttpStatus.OK);
    }

    @PutMapping("/user/{idUser}/rol/{idRol}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addRolUser(@PathVariable Integer idUser,@PathVariable Long idRol){
        return new ResponseEntity<>(userService.addUserRol(idUser,idRol), HttpStatus.OK);
    }

    @GetMapping("/findAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/findByEmail")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getByEmail(@RequestBody User user){
        return new ResponseEntity<>(userService.findByEmail(user.getEmail()), HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<User>> getByEmail(@PathVariable Integer id){
        return new ResponseEntity<>(userService.findByIdUser(id), HttpStatus.OK);
    }

    @GetMapping("/findUserById/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Optional<UserDTO>> getUserDTOById(@PathVariable Integer id,@RequestHeader(HttpHeaders.AUTHORIZATION) String JwtToken){
        return new ResponseEntity<>(userService.validateUserByToken(id,JwtToken), HttpStatus.OK);
    }


}
