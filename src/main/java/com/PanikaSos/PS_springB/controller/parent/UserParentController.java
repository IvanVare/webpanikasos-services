package com.PanikaSos.PS_springB.controller.parent;

import com.PanikaSos.PS_springB.request.UserParentServiceRequest;
import com.PanikaSos.PS_springB.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gateway/userParent")
@PreAuthorize("denyAll()")
public class UserParentController {
    @Autowired
    private UserParentServiceRequest userParentServiceRequest;
    @Autowired
    private UserService userService;

    @PostMapping("/create/{userId}/user/{parentId}/parent")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> create(@PathVariable Long userId, @PathVariable Long parentId){
        userService.findByIdUser(userId.intValue());
        return new ResponseEntity<>(userParentServiceRequest.create(userId,parentId), HttpStatus.CREATED);
    }

    @GetMapping("/findAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(userParentServiceRequest.getAll(),HttpStatus.OK);
    }

    @GetMapping("/findByParentId/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getByParentId(@PathVariable Long id){
        return new ResponseEntity<>(userParentServiceRequest.getByParentId(id),HttpStatus.OK);
    }

    @GetMapping("/findByUserId/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getByUserId(@PathVariable Long id){
        return new ResponseEntity<>(userParentServiceRequest.getByUserId(id),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}/user/{parentId}/parent")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long userId,@PathVariable Long parentId){
        userParentServiceRequest.delete(userId,parentId);
        return new ResponseEntity<>("UserParent deleted",HttpStatus.OK);
    }
}
