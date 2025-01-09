package com.PanikaSos.PS_springB.model.dto;

import com.PanikaSos.PS_springB.model.Rol;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControlUserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private int status;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Rol> cuser_rol = new HashSet<>();

}
