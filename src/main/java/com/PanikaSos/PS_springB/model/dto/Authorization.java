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
public class Authorization {

    private String token;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Rol> rol = new HashSet<>();
}
