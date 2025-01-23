package com.PanikaSos.PS_springB.request;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        value = "user-parent-service",
        path = "/api/userParent",
        url = "${parent.service.url}",
        //Desactivar si se enciende eureka Server
        configuration = FeignConfiguration.class
)
public interface UserParentServiceRequest {

    @PostMapping("/create/{userId}/user/{parentId}/parent")
    Object create(@PathVariable Long userId, @PathVariable Long parentId);

    @GetMapping("/findAll")
    List<Object> getAll();

    @GetMapping("/findByParentId/{id}")
    List<Object> getByParentId(@PathVariable Long id);

    @GetMapping("/findByUserId/{id}")
    List<Object> getByUserId(@PathVariable Long id);

    @DeleteMapping("/delete/{userId}/user/{parentId}/parent")
    void delete(@PathVariable Long userId,@PathVariable Long parentId);
}
