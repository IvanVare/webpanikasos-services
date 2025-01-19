package com.PanikaSos.PS_springB.controller;

import com.PanikaSos.PS_springB.request.ContactServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/gateway/contact")
@PreAuthorize("denyAll()")
public class ContactController {

    @Autowired
    private ContactServiceRequest contactServiceRequest;

    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> registerContact(@RequestBody Object contact){
        return new ResponseEntity<>(contactServiceRequest.register(contact), HttpStatus.CREATED);
    }

    @PostMapping("/uploadContacts")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> uploadContacts(@RequestBody Set<Object> contactSet){
        return new ResponseEntity<>(contactServiceRequest.uploadContacts(contactSet),HttpStatus.CREATED);
    }

    @GetMapping("/findAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findAllContact(){
        return new ResponseEntity<>(contactServiceRequest.findAll(), HttpStatus.OK);
    }
    @GetMapping("/findAllByUserId/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> findAllByUserIdContact(@PathVariable Long id){
        return new ResponseEntity<>(contactServiceRequest.findAllByUserId(id), HttpStatus.OK);
    }
    @GetMapping("/findById/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return new ResponseEntity<>(contactServiceRequest.findById(id), HttpStatus.OK);
    }
    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> update(@RequestBody Object contact){
        return new ResponseEntity<>(contactServiceRequest.update(contact), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id){
        contactServiceRequest.delete(id);
        return new ResponseEntity<>("Contacto borrado",HttpStatus.OK);
    }

}
