package com.PanikaSos.PS_springB.model.dto;

import com.PanikaSos.PS_springB.model.Rol;
import com.PanikaSos.PS_springB.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id_user;
    private String first_name;
    private String last_name;
    private String phoneNumber;
    private String age;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String email;

    private int status;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Rol> user_rol = new HashSet<>();


    //Pasar de una entidad User a un UserDTO
    public UserDTO(Optional<User> user) {
        this.id_user = user.get().getId_user();
        this.first_name = user.get().getFirst_name();
        this.last_name = user.get().getLast_name();
        this.phoneNumber = user.get().getPhoneNumber();
        this.age = user.get().getAge();
        this.email = user.get().getEmail();
        this.status = user.get().getStatus();
    }

    public UserDTO(User user) {
        this.id_user = user.getId_user();
        this.first_name = user.getFirst_name();
        this.last_name = user.getLast_name();
        this.phoneNumber = user.getPhoneNumber();
        this.age = user.getAge();
        this.email = user.getEmail();
        this.status = user.getStatus();
    }
}
