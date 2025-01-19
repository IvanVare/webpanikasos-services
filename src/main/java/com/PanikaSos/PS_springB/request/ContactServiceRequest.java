package com.PanikaSos.PS_springB.request;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@FeignClient(
        value = "contact-service",
        path = "/api/contact",
        url = "${contact.service.url}",
        //Desactivar si se enciende eureka Server
        configuration = FeignConfiguration.class
)
public interface ContactServiceRequest {

    @PostMapping("/register")
    Object register(@RequestBody Object requestBody);

    @PostMapping("/uploadContacts")
    Set<Object> uploadContacts(@RequestBody Set<Object> requestBodySet);

    @GetMapping("/findAll")
    List<Object> findAll();

    @GetMapping("/findAllByUserId/{id}")
    Set<Object> findAllByUserId(@PathVariable Long id);

    @GetMapping("/findById/{id}")
    Optional<Object> findById(@PathVariable Long id);

    @PutMapping("/update")
    Object update(@RequestBody Object requestBody);

    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable Long id);

}
