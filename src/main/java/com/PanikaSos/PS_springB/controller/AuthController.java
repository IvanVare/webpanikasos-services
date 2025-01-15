package com.PanikaSos.PS_springB.controller;

import com.PanikaSos.PS_springB.model.User;
import com.PanikaSos.PS_springB.model.dto.AuthResponse;
import com.PanikaSos.PS_springB.model.dto.LoginDTO;
import com.PanikaSos.PS_springB.model.dto.UserDTO;
import com.PanikaSos.PS_springB.security.UserDetailServiceImpl;
import com.PanikaSos.PS_springB.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
//@PreAuthorize("denyAll()")
public class AuthController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<UserDTO> Register(@RequestBody @Validated User user){
        return new ResponseEntity<>(userService.register(user),HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> Login(@RequestBody @Validated LoginDTO loginDTO){
        return new ResponseEntity<>(userDetailService.login(loginDTO),HttpStatus.OK);
    }


}
