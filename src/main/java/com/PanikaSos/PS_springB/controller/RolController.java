package com.PanikaSos.PS_springB.controller;

import com.PanikaSos.PS_springB.model.Rol;
import com.PanikaSos.PS_springB.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rol")
public class RolController {
    @Autowired
    private RolService rolService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Rol rol){
        return new ResponseEntity<>(rolService.saveRol(rol), HttpStatus.CREATED);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Rol>> findAllRols(){
        return new ResponseEntity<>(rolService.findAllRols(),HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateRol(@RequestBody Rol rol){
        return new ResponseEntity<>(rolService.saveRol(rol),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRol(@PathVariable Long id){
        rolService.delelteRol(id);
        return new ResponseEntity<>("Rol borrado",HttpStatus.OK);
    }



}
