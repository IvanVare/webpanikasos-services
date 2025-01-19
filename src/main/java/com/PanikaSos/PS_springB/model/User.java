package com.PanikaSos.PS_springB.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Table(name = "user_reg")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false)
    private Integer id;
    @NotBlank(message = "First name is required")
    @Column(name = "first_name_user", nullable = false, length = 100)
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Column(name = "last_name_user", nullable = false, length = 100)
    private String lastName;
    @NotBlank(message = "Phone number is required")
    @Column(name = "phone_number_user", unique = true, nullable = false, length = 30)
    private String phoneNumber;
    @NotBlank(message = "Age is required")
    @Column(name = "age_user", nullable = false, length = 10)
    private String age;
    @NotBlank(message = "Email is required")
    @Column(name = "email_user", unique = true, nullable = false, length = 100)
    private String email;
    @NotBlank(message = "Password is required")
    @Column(name = "password_user", nullable = false, length = 100)
    private String password;
    @Column(name = "status_user", nullable = false, length = 1)
    private int status;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_rol",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    private Set<Rol> user_rol = new HashSet<>();
}
