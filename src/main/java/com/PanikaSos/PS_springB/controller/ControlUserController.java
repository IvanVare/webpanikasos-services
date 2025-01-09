package com.PanikaSos.PS_springB.controller;

import com.PanikaSos.PS_springB.model.ControlUser;
import com.PanikaSos.PS_springB.model.dto.ControlUserDTO;
import com.PanikaSos.PS_springB.service.ControlUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/controlUser")
public class ControlUserController {
    @Autowired
    private ControlUserService controlUserService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody ControlUser controlUser){
        return new ResponseEntity<>(controlUserService.saveControlUser(controlUser), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCUser(@PathVariable Long id){
        controlUserService.deleteControlUser(id);
        return new ResponseEntity<>("Usuario borrado",HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateCUser(@RequestBody ControlUserDTO controlUserDTO){
        return new ResponseEntity<>(controlUserService.updateControlUser(controlUserDTO), HttpStatus.OK);
    }

    @GetMapping("/findAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ControlUserDTO>> findAllCUsers(){
        return new ResponseEntity<>(controlUserService.findAllControlUser(), HttpStatus.OK);
    }

    @GetMapping("/findById/{idUser}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ControlUserDTO> findByIdCUsers(@PathVariable Long idUser){
        ControlUserDTO user = controlUserService.findByIdControlUser(idUser);
        return ResponseEntity.ok(user);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/user/{idUser}/rol/{idRol}")
    public ResponseEntity<?> addRolUser(@PathVariable Long idUser,@PathVariable Long idRol){
        return new ResponseEntity<>(controlUserService.addUserRol(idUser,idRol), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/{idUser}")
    public ResponseEntity<?> addRolUser(@PathVariable Long idUser){
        return new ResponseEntity<>(controlUserService.findRolesByIdUser(idUser), HttpStatus.OK);
    }
}
