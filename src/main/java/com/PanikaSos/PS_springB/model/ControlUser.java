package com.PanikaSos.PS_springB.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Table(name = "cuser")
@Entity
public class ControlUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;
    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;
    @Column(name = "password", nullable = false, length = 100)
    private String password;
    @Column(name = "status", nullable = false, length = 1)
    private int status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "cuser_rol",
            joinColumns = @JoinColumn(name = "id_cuser"),
            inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    private Set<Rol> cuser_rol = new HashSet<>();
}
