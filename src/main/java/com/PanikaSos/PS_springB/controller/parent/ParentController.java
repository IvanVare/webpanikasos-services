package com.PanikaSos.PS_springB.controller.parent;

import com.PanikaSos.PS_springB.request.ParentServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/gateway/parent")
@PreAuthorize("denyAll()")
public class ParentController {
    @Autowired
    private ParentServiceRequest parentServiceRequest;


    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> register(@RequestBody Object parent){
        return new ResponseEntity<>(parentServiceRequest.register(parent), HttpStatus.CREATED);
    }

    @GetMapping("/findAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(parentServiceRequest.getAll(), HttpStatus.OK);
    }
    @GetMapping("/findById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return new ResponseEntity<>(parentServiceRequest.getById(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> update(@RequestBody Object parent){
        return new ResponseEntity<>(parentServiceRequest.update(parent), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id){
        parentServiceRequest.delete(id);
        return new ResponseEntity<>("Parent deleted",HttpStatus.OK);
    }
}
