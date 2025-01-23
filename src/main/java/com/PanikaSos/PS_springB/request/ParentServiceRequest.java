package com.PanikaSos.PS_springB.request;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(
        value = "parent-service",
        path = "/api/parent",
        url = "${parent.service.url}",
        //Desactivar si se enciende eureka Server
        configuration = FeignConfiguration.class
)
public interface ParentServiceRequest {

    @PostMapping("/register")
    Object register(@RequestBody Object requestBody);

    @GetMapping("/findAll")
    List<Object> getAll();

    @GetMapping("/findById/{id}")
    Optional<Object> getById(@PathVariable Long id);

    @PutMapping("/update")
    Object update(@RequestBody Object requestBody);

    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable Long id);
}
