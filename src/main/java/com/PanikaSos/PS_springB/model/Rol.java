package com.PanikaSos.PS_springB.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Table(name = "rol")
@Entity
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, length = 30)
    private String name;
    @Column(name = "status", nullable = false, length = 1)
    private int status;

    @JsonIgnore
    @ManyToMany(mappedBy = "cuser_rol")
    private Set<ControlUser> Cusers = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "user_rol")
    private Set<User> users = new HashSet<>();
}
